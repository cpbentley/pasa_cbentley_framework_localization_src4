package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.interfaces.IJavaObjectFactory;
import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.i8n.LocaleID;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.localization.src4.engine.StrLoader;
import pasa.cbentley.powerdata.spec.src4.IFactoryIDStruct;
import pasa.cbentley.powerdata.spec.src4.ctx.PDCtxA;
import pasa.cbentley.powerdata.spec.src4.power.IPowerCharCollector;

public class StrLoaderCtx extends ACtx {

   private BOCtx          boc;

   protected final PDCtxA pdc;

   private StrLoader      strLoader;

   public StrLoaderCtx(UCtx uc, BOCtx boc, PDCtxA pdc, LocaleID[] lids) {
      super(uc);
      this.boc = boc;
      this.pdc = pdc;
      strLoader = new StrLoader(this, lids);
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
      dc.nlLvlNoTitle("Factory", getFactory());
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
