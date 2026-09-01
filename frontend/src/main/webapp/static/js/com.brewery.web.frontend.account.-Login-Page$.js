'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dLogin$0024 from "./com.brewery.web.frontend.account.-Login$.js";
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_account_LoginPage$() {
}
export { $c_Lcom_brewery_web_frontend_account_LoginPage$ as $c_Lcom_brewery_web_frontend_account_LoginPage$ };
$c_Lcom_brewery_web_frontend_account_LoginPage$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_account_LoginPage$.prototype.constructor = $c_Lcom_brewery_web_frontend_account_LoginPage$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_account_LoginPage$() {
}
export { $h_Lcom_brewery_web_frontend_account_LoginPage$ as $h_Lcom_brewery_web_frontend_account_LoginPage$ };
$h_Lcom_brewery_web_frontend_account_LoginPage$.prototype = $c_Lcom_brewery_web_frontend_account_LoginPage$.prototype;
$c_Lcom_brewery_web_frontend_account_LoginPage$.prototype.initialize__V = (function() {
  var loginFormValidation = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element, jQueryEvent) => {
    jQueryEvent.preventDefault();
    $j_com$002ebrewery$002eweb$002efrontend$002eaccount$002e$002dLogin$0024.$m_Lcom_brewery_web_frontend_account_Login$().loginValidation__Lorg_scalajs_dom_HTMLFormElement__V(element);
  }));
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_Lio_udash_wrappers_jquery_JQuery$();
  var jquery = (0, $i_jquery.default)("#login-form");
  new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_Lio_udash_wrappers_jquery_JQuery$JQueryWrapper(jquery).on__T__F2__Lio_udash_wrappers_jquery_JQuery("submit", new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction2_$$Lambda$286cbfc6187197affcadc8465aaec93d6b7d20dc(((element$2, jQueryEvent$2) => loginFormValidation.apply__O__O__O(element$2, jQueryEvent$2))));
});
var $d_Lcom_brewery_web_frontend_account_LoginPage$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_account_LoginPage$, "com.brewery.web.frontend.account.LoginPage$", ({
  Lcom_brewery_web_frontend_account_LoginPage$: 1
}));
export { $d_Lcom_brewery_web_frontend_account_LoginPage$ as $d_Lcom_brewery_web_frontend_account_LoginPage$ };
var $n_Lcom_brewery_web_frontend_account_LoginPage$;
function $m_Lcom_brewery_web_frontend_account_LoginPage$() {
  if ((!$n_Lcom_brewery_web_frontend_account_LoginPage$)) {
    $n_Lcom_brewery_web_frontend_account_LoginPage$ = new $c_Lcom_brewery_web_frontend_account_LoginPage$();
  }
  return $n_Lcom_brewery_web_frontend_account_LoginPage$;
}
export { $m_Lcom_brewery_web_frontend_account_LoginPage$ as $m_Lcom_brewery_web_frontend_account_LoginPage$ };
//# sourceMappingURL=com.brewery.web.frontend.account.-Login-Page$.js.map
