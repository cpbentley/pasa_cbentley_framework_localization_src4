package pasa.cbentley.framework.localization.src4.engine;

import pasa.cbentley.core.src4.i8n.IString;
import pasa.cbentley.core.src4.i8n.LString;
import pasa.cbentley.framework.localization.src4.ctx.StrLoaderCtx;

/**
 * Encapsulates the localized String.
 * <br>
 * <br>
 * This enables Hot reloads
 * @author Charles-Philip Bentley
 *
 */
public class StrLocal extends LString implements IString {

   public StrLoaderCtx loader;

   /**
    * This module only deals with
    * @param loader
    * @param id
    */
   StrLocal(StrLoaderCtx loader, int id) {
      super(loader.getUCtx(), id, null);
      this.loader = loader;
   }

   public String toString() {
      return getStr();
   }

   public String getStr() {
      return loader.getLoader().getString(keyInt);
   }
}
