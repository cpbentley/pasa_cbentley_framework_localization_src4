package pasa.cbentley.framework.localization.src4.engine;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.IFlagsToString;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.IString;
import pasa.cbentley.core.src4.i8n.IStringMapper;
import pasa.cbentley.core.src4.i8n.IStringProducer;
import pasa.cbentley.core.src4.i8n.LString;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.i8n.StringProducerAbstract;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.core.src4.structs.IntToStrings;
import pasa.cbentley.framework.localization.src4.ctx.LocalizationCtx;
import pasa.cbentley.powerdata.spec.src4.power.IPowerCharCollector;

/**
 * Class that loads strings into a {@link IPowerCharCollector} using the active language suffix
 * <br>
 * <br>
 * Changing the suffix reloads all the {@link IPowerCharCollector}
 * <br>
 * <br>
 * How to use this class.
 * <br>
 * <br>
 * Applications using this package
 * 
 * 
 * @author Charles-Philip Bentley
 *
 */
public class StrLoader extends StringProducerAbstract implements IStringMapper, IStringProducer, IStringable {

   public static final int         MODULE_ID = 15;

   private IntToStrings            dataModel;

   protected final LocalizationCtx loc;

   private int                     staticForStringIDs;

   protected final UCtx            uc;

   /**
    * Contains all the {@link CtxStringsData}
    */
   protected IntToObjects          v;

   /**
    * TODO When the App launcher launches, it decides which number of additional slots are used.
    * <br>
    * It loads available languages from settings.
    *<br>
    *<br>
    * @param num
    */
   public StrLoader(LocalizationCtx loc, LocaleID[] lids) {
      super(loc.getUCtx(), lids);
      this.loc = loc;
      this.uc = loc.getUCtx();
      v = new IntToObjects(uc);
   }

   /**
    * TODO ask user if profile based or device based.
    * Data can be copied profile <-> device
    */
   public LocaleID addLocaleID(String name, String suffix) {
      LocaleID lid = new LocaleID(uc, name, suffix);
      LocaleID[] old = lids;
      lids = new LocaleID[old.length];
      for (int i = 0; i < old.length; i++) {
         lids[i] = old[i];
      }
      lids[lids.length - 1] = lid;
      //TODO write in database
      return lid;
   }

   /**
    * Disposes of the {@link IPowerCharCollector} but not of the data needed to reload it.
    * <br>
    * <br>
    * 
    * @param id
    */
   public void dispose(int id) {
      CtxStringsData sm = (CtxStringsData) v.getObjectAtIndex(id);
      sm.getCharscollector().closeAgent();
      sm.setCharscollector(null);
   }

   public StrLocal get(int id) {
      StrLocal sl = new StrLocal(loc, id);
      return sl;
   }

   /**
    * Extract the id of {@link IPowerCharCollector}.
    * <br>
    * <br>
    * 
    * @param id
    * @return
    */
   public char[] getCharArray(int id) {
      int ctxIDCharColID = LocUtils.getCtxIDFromLocID(id);
      int strID = LocUtils.getStrIDFromLocID(id);
      IPowerCharCollector cc = getCharCol(strID, ctxIDCharColID);
      return cc.getChars(strID);
   }

   /**
    * Returns the char collector
    * @param sid
    * @param mod
    * @return
    */
   private IPowerCharCollector getCharCol(int sid, int mod) {
      CtxStringsData sm = (CtxStringsData) v.findIntObject(mod);
      if (sm != null) {
         if (sm.getCharscollector() == null) {
            sm.loadStringDataStruct();
         }
         return sm.getCharscollector();
      } else {
         throw new IllegalArgumentException("ModuleID = " + mod + " not found. StringID=" + sid);
      }
   }

   public IString getIString(int key) {
      StrLocal sl = new StrLocal(loc, key);
      return sl;
   }

   public IString getIString(String key, String def) {
      LString sl = new LString(this, key, def);
      return sl;
   }

   public IString getIString(String key, String def, String suffix) {
      LString sl = new LString(this, key, def);
      sl.setSuffix(suffix);
      return sl;
   }

   public IString getIStringKey(int key, String def) {
      LString sl = new LString(this, key, def);
      return sl;
   }

   /**
    * Returns language suffixes with the available user string.
    * <br>
    * Returns a String[] array with avialable languages and their identifying 
    * suffix.
    * <br>
    *
    * @return
    */
   public String[] getLanguageDataModel() {
      if (dataModel == null) {
         IntToStrings its = new IntToStrings(uc);
         for (int i = 0; i < lids.length; i++) {
            its.add(lids[i].getSuffix());
            its.add(lids[i].getName());
         }
         dataModel = its;
      }
      return dataModel.getStrings();
   }

   public int getModuleID() {
      return MODULE_ID;
   }

   public int getProducerID() {
      // TODO Auto-generated method stub
      return 0;
   }

   /**
    * 
    * @param id
    * @return
    */
   public String getString(int id) {
      int mod = (id >> 16) & 0xFFFF;
      int sid = id & 0x0000FFFF;
      String str = "";
      try {
         IPowerCharCollector cc = getCharCol(sid, mod);
         str = cc.getKeyStringFromPointer(sid);
      } catch (Exception e) {
         e.printStackTrace();
         str = "String Error [" + mod + "," + sid + "]";
         //#debug
         toDLog().pEx("msg", this, StrLoader.class, "getString", e);
      }
      if (str == null || str.length() == 0) {
         str = "String  [" + mod + "," + sid + "]";
      }
      return str;
   }

   public String getString(String strID) {
      return strID;
   }

   public IStringMapper getStringMapper() {
      return this;
   }

   public void loads(ICtx mod, String pathid) {
      loads(mod, new String[] { pathid });
   }

   /**
    * Reads the files consecutively and add all those data to the
    * <br>
    * <br>
    * Translations are static or instances to the module?
    * static If we want a a different implementation. its possible
    * @param id
    * @param cl
    * @param fileRoots
    * @param langStr
    */
   public void loads(ICtx module, String[] fileRoots) {
      int id = module.getRegistrationID();
      CtxStringsData sm = new CtxStringsData(loc);
      sm.setLoader(module);
      sm.setPaths(fileRoots);
      v.ensureRoom(id, 1);
      int modid = module.getCtxID();
      v.setObjectAndInt(sm, modid, id);
      sm.loadStringDataStruct();
   }

   public String map(int key) {
      return getString(key);
   }

   public String map(String key) {
      return getString(key);
   }

   /**
    * Sets the new locale based on the suffix.
    * <br>
    * <br>
    * Look up a {@link LocaleID} with the given suffix.
    * 
    * @param suffix
    * @throw {@link IllegalArgumentException} when suffix is not found
    */
   public void setFileSuffix(String suffix) {
      LocaleID lc = getLocale(suffix);
      if (lc == null)
         throw new IllegalArgumentException();
      setLocalID(lc);
   }

   /**
    * 
    * @param key
    * @param str
    */
   public void setLocaleTrans(int key, String str) {

   }

   public void setLocalID(LocaleID lid) {
      //might be called in constructor
      super.setLocalID(lid);
   }

   public void setSettings(byte[] data) {
      // TODO Auto-generated method stub

   }

   public void setValue(ICtx cl, LocaleID lid, int key, String string) {
      throw new RuntimeException();
   }

   //#mdebug
   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, StrLoader.class);
      super.toString(dc);
      dc.nlVar("staticForStringIDs", staticForStringIDs);
      dc.nlLvl("Data Model", dataModel);
      dc.nl();
      //tell context we want full toString of sub objects
      dc.nlLvl("StModules", v, IFlagsToString.FLAG_1_EXPAND);
      //we want the DeviceDebug to appear here and never in other object
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this,StrLoader.class);
      current.toString1Line(dc);
   }

   //#enddebug

}
