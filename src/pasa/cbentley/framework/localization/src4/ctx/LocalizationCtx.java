package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.core.interfaces.IJavaObjectFactory;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.ILocale;
import pasa.cbentley.core.src4.i8n.I8nString;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.localization.src4.engine.LStringFramework;
import pasa.cbentley.framework.localization.src4.engine.StringProducerFramework;
import pasa.cbentley.powerdata.spec.src4.IFactoryIDStruct;
import pasa.cbentley.powerdata.spec.src4.ctx.PDCtxA;

/**
 * A {@link ByteObject} context {@link ABOCtx} for the code managing string localizations
 * 
 * <p>
 * This context uses PowerData {@link PDCtxA}. Concrete implementation of the PowerData API is unknown. 
 * {@link PDCtxA} is provided in the constructor and cannot be null.
 * </p>
 * 
 *  When loading without users settings, read all values.
 *  If settings have a locale. use it directly and do not load
 * <p>
 * {@link IConfigLoc} 
 * </p>
 * 
 * <p>
 * Implements the {@link I8nString} with {@link LStringFramework}
 * </p>
 * @author Charles Bentley
 *
 */
public class LocalizationCtx extends ABOCtx {

   public static final int         CTX_ID = 30;

   protected final PDCtxA          pdc;

   private StringProducerFramework strLoader;

   private String[]                locales;

   public LocalizationCtx(IConfigLoc config, UCtx uc, BOCtx boc, PDCtxA pdc) {
      super(config, boc);
      //#mdebug
      if (pdc == null) {
         throw new NullPointerException();
      }
      //#enddebug

      locales = config.getSupportedLocales();
      this.pdc = pdc;

      //TODO current locale saved in settings
      //which Locales to use? which is current.. 
      LocaleID[] lids = new LocaleID[1];
      lids[0] = new LocaleID(uc, ILocale.NAME_0_EN, ILocale.SUFFIX_0_EN);

      strLoader = new StringProducerFramework(this, lids);

      if (this.getClass() == LocalizationCtx.class) {
         a_Init();
      }
   }

   /**
    * Add the given local in the list of loaded.
    * 
    * Does nothing if already there.
    * @param locale
    */
   public void addLocale(LocaleID locale) {
      // TODO Auto-generated method stub

   }

   /**
    * Called by the {@link ABOCtx#a_Init()} method or whenever settings are changed.
    * 
    * Apply the current locale
    */
   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

      int id = settingsNew.get2(ITechCtxSettingsLoc.CTX_LOC_OFFSET_02_LOC_ID2);
      if(id < 0 || id >= locales.length) {
         throw new IllegalArgumentException();
      }
      String suffix = locales[id];
      String name = locales[id+1];
      LocaleID localID = new LocaleID(uc, name, suffix);

      //TODO apply local
   }

   public int getBOCtxSettingSize() {
      return ITechCtxSettingsLoc.CTX_LOC_BASIC_SIZE;
   }

   public int getCtxID() {
      return CTX_ID;
   }

   public IJavaObjectFactory getFactory() {
      return getPDC().getFactory(IFactoryIDStruct.ID);
   }

   public StringProducerFramework getLoader() {
      return strLoader;
   }

   public PDCtxA getPDC() {
      return pdc;
   }

   /**
    * We ignore saved settings. Burn config into settings {@link ByteObject}
    */
   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigLoc configLoc = (IConfigLoc) config;
      String suffix = configLoc.getLocalSuffix();
      int id = getLocaleIDFromSuffix(suffix);
      //check flag to see 
      settings.set2(ITechCtxSettingsLoc.CTX_LOC_OFFSET_02_LOC_ID2, id);

      if (suffix.length() != 2) {
         //bad input
         throw new IllegalArgumentException("config suffix must be 2 characters");
      }

   }

   private int getLocaleIDFromSuffix(String suffix) {
      // TODO Auto-generated method stub
      return 0;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "LocalizationCtx", "92");
      toStringPrivate(dc);
      dc.nlLvlNullTitle("Factory", getFactory());
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "LocalizationCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
