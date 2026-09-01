'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002eadmin$002e$002dAdmin$0024 from "./com.brewery.web.frontend.admin.-Admin$.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
import * as $j_internal$002d6cbcec0732cac4bcbf3d66c300b421fad7d904be from "./internal-6cbcec0732cac4bcbf3d66c300b421fad7d904be.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_admin_AdminPage$() {
}
export { $c_Lcom_brewery_web_frontend_admin_AdminPage$ as $c_Lcom_brewery_web_frontend_admin_AdminPage$ };
$c_Lcom_brewery_web_frontend_admin_AdminPage$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_admin_AdminPage$.prototype.constructor = $c_Lcom_brewery_web_frontend_admin_AdminPage$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_admin_AdminPage$() {
}
export { $h_Lcom_brewery_web_frontend_admin_AdminPage$ as $h_Lcom_brewery_web_frontend_admin_AdminPage$ };
$h_Lcom_brewery_web_frontend_admin_AdminPage$.prototype = $c_Lcom_brewery_web_frontend_admin_AdminPage$.prototype;
$c_Lcom_brewery_web_frontend_admin_AdminPage$.prototype.initialize__V = (function() {
  var $x_4 = $i_jquery.default;
  var $x_3 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$();
  var $x_2 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("url", "/account/roles");
  var $x_1 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("method", "POST");
  var _2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_Any$().fromFunction3__F3__sjs_js_Function3(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction3_$$Lambda$26e0a25d9b29f6b82ea50ab7badf4fb70c5c74e1(((data, textStatus, jqXHR) => {
    $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(textStatus);
    if ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uZ(data.success)) {
      var roles = data.data;
      if ($j_internal$002d6cbcec0732cac4bcbf3d66c300b421fad7d904be.$m_sjs_js_ArrayOps$().contains$extension__sjs_js_Array__O__Z(roles, "Admin")) {
        $j_com$002ebrewery$002eweb$002efrontend$002eadmin$002e$002dAdmin$0024.$m_Lcom_brewery_web_frontend_admin_Admin$().init__V();
      }
    }
  })));
  var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([$x_2, $x_1, new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("success", _2)]));
  $x_4.ajax($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
});
var $d_Lcom_brewery_web_frontend_admin_AdminPage$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_admin_AdminPage$, "com.brewery.web.frontend.admin.AdminPage$", ({
  Lcom_brewery_web_frontend_admin_AdminPage$: 1
}));
export { $d_Lcom_brewery_web_frontend_admin_AdminPage$ as $d_Lcom_brewery_web_frontend_admin_AdminPage$ };
var $n_Lcom_brewery_web_frontend_admin_AdminPage$;
function $m_Lcom_brewery_web_frontend_admin_AdminPage$() {
  if ((!$n_Lcom_brewery_web_frontend_admin_AdminPage$)) {
    $n_Lcom_brewery_web_frontend_admin_AdminPage$ = new $c_Lcom_brewery_web_frontend_admin_AdminPage$();
  }
  return $n_Lcom_brewery_web_frontend_admin_AdminPage$;
}
export { $m_Lcom_brewery_web_frontend_admin_AdminPage$ as $m_Lcom_brewery_web_frontend_admin_AdminPage$ };
//# sourceMappingURL=com.brewery.web.frontend.admin.-Admin-Page$.js.map
