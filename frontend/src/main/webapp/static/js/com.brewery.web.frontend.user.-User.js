'use strict';
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_user_User() {
  this.Lcom_brewery_web_frontend_user_User__f_userId = null;
  this.Lcom_brewery_web_frontend_user_User__f_username = null;
  this.Lcom_brewery_web_frontend_user_User__f_roles = null;
  this.Lcom_brewery_web_frontend_user_User__f_userId = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_s_None$();
  this.Lcom_brewery_web_frontend_user_User__f_username = "";
  $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$ct_ju_HashSet__(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_ju_HashSet());
  this.Lcom_brewery_web_frontend_user_User__f_roles = [];
}
export { $c_Lcom_brewery_web_frontend_user_User as $c_Lcom_brewery_web_frontend_user_User };
$c_Lcom_brewery_web_frontend_user_User.prototype = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$h_O();
$c_Lcom_brewery_web_frontend_user_User.prototype.constructor = $c_Lcom_brewery_web_frontend_user_User;
/** @constructor */
function $h_Lcom_brewery_web_frontend_user_User() {
}
export { $h_Lcom_brewery_web_frontend_user_User as $h_Lcom_brewery_web_frontend_user_User };
$h_Lcom_brewery_web_frontend_user_User.prototype = $c_Lcom_brewery_web_frontend_user_User.prototype;
$c_Lcom_brewery_web_frontend_user_User.prototype.toJsObject__sjs_js_Dynamic = (function() {
  var $x_3 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
  var s = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_ju_UUID($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(this.Lcom_brewery_web_frontend_user_User__f_userId).get__O())).toString__T();
  var $x_2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("userId", s);
  var s$1 = this.Lcom_brewery_web_frontend_user_User__f_username;
  var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("username", s$1);
  var _2 = this.Lcom_brewery_web_frontend_user_User__f_roles;
  var fields = $x_3.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_2, $x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("roles", _2)]));
  return $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
});
var $d_Lcom_brewery_web_frontend_user_User = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$TypeData().initClass($c_Lcom_brewery_web_frontend_user_User, "com.brewery.web.frontend.user.User", ({
  Lcom_brewery_web_frontend_user_User: 1
}));
export { $d_Lcom_brewery_web_frontend_user_User as $d_Lcom_brewery_web_frontend_user_User };
//# sourceMappingURL=com.brewery.web.frontend.user.-User.js.map
