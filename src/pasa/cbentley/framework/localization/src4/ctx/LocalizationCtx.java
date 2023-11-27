package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.ABOCtx;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.byteobjects.src4.interfaces.IJavaObjectFactory;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.ILocale;
import pasa.cbentley.core.src4.i8n.IString;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.localization.src4.engine.StrLoader;
import pasa.cbentley.framework.localization.src4.engine.StrLocal;
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
 * <p>
 * {@link IConfigLoc} 
 * </p>
 * 
 * <p>
 * Implements the {@link IString} with {@link StrLocal}
 * </p>
 * @author Charles Bentley
 *
 */
public class LocalizationCtx extends ABOCtx {

   public static final int CTX_ID = 300;

   protected final PDCtxA  pdc;

   private StrLoader       strLoader;

   public LocalizationCtx(IConfigLoc config, UCtx uc, BOCtx boc, PDCtxA pdc) {
      super(config, boc);
      this.pdc = pdc;

      //TODO current locale saved in settings
      //which Locales to use? which is current.. 
      LocaleID[] lids = new LocaleID[1];
      lids[0] = new LocaleID(uc, ILocale.NAME_0_EN, ILocale.SUFFIX_0_EN);

      strLoader = new StrLoader(this, lids);
   }

   public int getCtxID() {
      return CTX_ID;
   }

   protected void matchConfig(IConfigBO config, ByteObject settings) {
      IConfigLoc configLoc = (IConfigLoc) config;
   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {

   }

   public int getBOCtxSettingSize() {
      return ITechCtxSettingsLocalization.MODSET_LOC_BASIC_SIZE;
   }

   public StrLoader getLoader() {
      return strLoader;
   }

   public PDCtxA getPDC() {
      return pdc;
   }

   public IJavaObjectFactory getFactory() {
      return getPDC().getFactory(IFactoryIDStruct.ID);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "StrLoaderCtx");
      toStringPrivate(dc);
      dc.nlLvlNullTitle("Factory", getFactory());
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StrLoaderCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
