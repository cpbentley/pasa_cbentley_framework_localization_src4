package pasa.cbentley.framework.localization.src4.ctx;

import pasa.cbentley.byteobjects.src4.ctx.ConfigAbstractBO;
import pasa.cbentley.core.src4.ctx.UCtx;

/**
 * Default implementation of {@link IConfigLoc}
 * 
 * @author Charles Bentley
 *
 */
public class ConfigLocDefault extends ConfigAbstractBO implements IConfigLoc {

   public ConfigLocDefault(UCtx uc) {
      super(uc);
   }

   public String getLocalSuffix() {
      return "en";
   }

   public String getSingleLocaleSuffix() {
      return "en";
   }

   public String[] getSupportedLocales() {
      return BentleyLocales.get();
   }

}
