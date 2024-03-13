package pasa.cbentley.framework.localization.src4.engine;

import java.io.InputStream;

import pasa.cbentley.byteobjects.src4.core.ByteObjectManaged;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.io.FileReader;
import pasa.cbentley.core.src4.io.XString;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.localization.src4.ctx.LocalizationCtx;
import pasa.cbentley.powerdata.spec.src4.engine.MorphTech;
import pasa.cbentley.powerdata.spec.src4.power.IPowerCharCollector;
import pasa.cbentley.powerdata.spec.src4.power.IPowerDataTypes;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechMorph;
import pasa.cbentley.powerdata.spec.src4.power.itech.ITechPointerStruct;

/**
 * Base class for access data objects of {@link LocalizationCtx}
 * 
 * @author Charles Bentley
 *
 */
public class CtxStringsData implements IStringable {

   protected final LocalizationCtx loc;

   protected final UCtx            uc;

   private IPowerCharCollector     charscollector;

   private ICtx                    ctx;

   private String[]                paths;

   public CtxStringsData(LocalizationCtx loc) {
      this.loc = loc;
      //#mdebug
      if (loc == null) {
         throw new NullPointerException();
      }
      //#enddebug
      this.uc = loc.getUC();
   }

   public IPowerCharCollector getCharscollector() {
      return charscollector;
   }

   public void setCharscollector(IPowerCharCollector charscollector) {
      this.charscollector = charscollector;
   }

   /**
    * The root paths (i.e. without the suffix) used by this module
    * @return
    */
   public String[] getPaths() {
      return paths;
   }

   public void setPaths(String[] paths) {
      this.paths = paths;
   }

   public ICtx getLoader() {
      return ctx;
   }

   public void loadStringDataStruct() {
      //we define what kind of char collector we want. Factory will create one for us.
      MorphTech morphTech = loc.getPDC().getMorphTech();
      ByteObjectManaged tech = morphTech.getPointerTech(IPowerDataTypes.INT_22_CHAR_COLLECTOR, ITechMorph.MODE_1_BUILD);
      //the tech? we need to define the start pointer
      tech.set4(ITechPointerStruct.PS_OFFSET_04_START_POINTER4, 0);
      //tech.set2(IObjectManaged.AGENT_OFFSET_02_CLASS_ID2, IPowerDataTypes.CLASS_TYPE_44_CHAR_COL_BUILD);
      IPowerCharCollector cc = (IPowerCharCollector) loc.getFactory().createObject(tech, IPowerCharCollector.INT_ID);

      //#debug
      toDLog().pInit("Localizor for " + this.toString1Line() + " IPowerCharCollector", cc, CtxStringsData.class, "loadCC", LVL_05_FINE, true);

      this.charscollector = cc;
      readStrings(cc);

   }

   /**
    * Loads the languages locales supported by the files roots
    */
   public void loadRootPath() {
      String[] fileRoots = paths;
      Object cl = ctx;
      FileReader fr = new FileReader(uc);
      XString cp = null;
      for (int i = 0; i < fileRoots.length; i++) {
         String file = "/" + fileRoots[i] + ".txt";
         InputStream is = uc.getIOU().getStream(cl, file);
         if (is == null) {
            throw new NullPointerException("Class " + cl.getClass().getName() + " could not read file " + file);
         }

         fr.open(is, "UTF-8");
         //problem with bom, there a lie with nothing
         fr.readCharLine();
         while ((cp = fr.readCharLine()) != null) {
            char[] chars = cp.getChars();
            int offset = cp.getOffset();
            int len = cp.getLen();
            if (chars[offset] == '#') {
               continue;
            }
            //read the first 2 letters
            String suffix = new String(chars, offset, 2);
            String lang = new String(chars, offset + 3, offset + len);
            LocaleID locale = new LocaleID(uc, lang, suffix);
            loc.addLocale(locale);
            
            
         }
         fr.close();
      }
      
      //then load txt files
   }

   /**
    * Loads all the strings from the file roots into the {@link IPowerCharCollector}.
    * <br>
    * <br>
    * 
    * @param cl
    * @param fileRoots
    * @param cc
    * @param suffix
    */
   public void readStrings(IPowerCharCollector cc) {
      String suffix = loc.getLoader().getLocaleID().getSuffix();
      String[] fileRoots = paths;
      Object cl = ctx;
      FileReader fr = new FileReader(uc);
      XString cp = null;
      for (int i = 0; i < fileRoots.length; i++) {
         String file = "/" + fileRoots[i] + "_" + suffix + ".txt";
         InputStream is = uc.getIOU().getStream(cl, file);
         if (is == null && suffix != "en") {
            file = "/" + fileRoots[i] + "_en.txt";
            is = uc.getIOU().getStream(cl, file);
         }
         if (is == null) {
            throw new NullPointerException("Class " + cl.getClass().getName() + " could not read file " + file);
         }
         fr.open(is, "UTF-8");
         //problem with bom, there a lie with nothing
         fr.readCharLine();
         while ((cp = fr.readCharLine()) != null) {
            char[] chars = cp.getChars();
            int offset = cp.getOffset();
            if (chars[offset] == '#') {
               continue;
            }
            //add anyways even if no strings
            //do they use reference or do a copy?
            cc.addChars(chars, offset, cp.getLen());
         }
         fr.close();
      }
   }

   /**
    * Sets the {@link ICtx} for this {@link CtxStringsData}
    * 
    * @param loader
    */
   public void setLoader(ICtx loader) {
      this.ctx = loader;
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "StModule");
      toStringPrivate(dc);
      dc.nlLvlArray1Line(paths, "Paths");
      dc.nlLvl("CharCollector", charscollector);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StModule");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return loc.getUC();
   }

   //#enddebug

}
