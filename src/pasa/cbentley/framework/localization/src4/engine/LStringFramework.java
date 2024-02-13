package pasa.cbentley.framework.localization.src4.engine;

import pasa.cbentley.core.src4.i8n.I8nString;
import pasa.cbentley.core.src4.i8n.LString;
import pasa.cbentley.framework.localization.src4.ctx.LocalizationCtx;

/**
 * Encapsulates the localized String.
 * <br>
 * <br>
 * This enables Hot reloads
 * @author Charles-Philip Bentley
 *
 */
public class LStringFramework extends LString implements I8nString {

   public LocalizationCtx loader;

   /**
    * This module only deals with
    * @param loader
    * @param id
    */
   LStringFramework(LocalizationCtx loader, int id) {
      super(loader.getLoader(), id, null);
      this.loader = loader;
   }

   public String toString() {
      return getStr();
   }

   public String getStr() {
      return loader.getLoader().getString(keyInt);
   }
}
