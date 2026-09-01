'use strict';
import * as $i_jquery from "jquery";
import * as $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024 from "./com.brewery.web.frontend.dom.-Page-Initializers$.js";
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_Main$() {
  this.Lcom_brewery_web_frontend_Main$__f_methods = null;
  $n_Lcom_brewery_web_frontend_Main$ = this;
  var this$10 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sci_Map$();
  var $x_4 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sr_ScalaRunTime$();
  var y = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction0_$$Lambda$a02b774b97db8234e08c6a02dd06557c99779855((() => {
    $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024.$m_Lcom_brewery_web_frontend_dom_PageInitializers$().conversations__V();
  }));
  var $x_3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("/conversations", y);
  var y$1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction0_$$Lambda$a02b774b97db8234e08c6a02dd06557c99779855((() => {
    $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024.$m_Lcom_brewery_web_frontend_dom_PageInitializers$().login__V();
  }));
  var $x_2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("/account/login", y$1);
  var y$2 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction0_$$Lambda$a02b774b97db8234e08c6a02dd06557c99779855((() => {
    $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024.$m_Lcom_brewery_web_frontend_dom_PageInitializers$().register__V();
  }));
  var $x_1 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("/account/register", y$2);
  var y$3 = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction0_$$Lambda$a02b774b97db8234e08c6a02dd06557c99779855((() => {
    $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024.$m_Lcom_brewery_web_frontend_dom_PageInitializers$().admin__V();
  }));
  var elems = $x_4.wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$d_T2.getArrayOf().constr)([$x_3, $x_2, $x_1, new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_T2("/admin", y$3)]));
  this.Lcom_brewery_web_frontend_Main$__f_methods = this$10.from__sc_IterableOnce__sci_Map(elems);
}
export { $c_Lcom_brewery_web_frontend_Main$ as $c_Lcom_brewery_web_frontend_Main$ };
$c_Lcom_brewery_web_frontend_Main$.prototype = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$h_O();
$c_Lcom_brewery_web_frontend_Main$.prototype.constructor = $c_Lcom_brewery_web_frontend_Main$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_Main$() {
}
export { $h_Lcom_brewery_web_frontend_Main$ as $h_Lcom_brewery_web_frontend_Main$ };
$h_Lcom_brewery_web_frontend_Main$.prototype = $c_Lcom_brewery_web_frontend_Main$.prototype;
$c_Lcom_brewery_web_frontend_Main$.prototype.main__AT__V = (function(args) {
  var pathname = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_T(window.location.pathname);
  (0, $i_jquery.default)($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_sjs_js_Any$().fromFunction0__F0__sjs_js_Function0(new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_sr_AbstractFunction0_$$Lambda$a02b774b97db8234e08c6a02dd06557c99779855((() => {
    $j_com$002ebrewery$002eweb$002efrontend$002edom$002e$002dPage$002dInitializers$0024.$m_Lcom_brewery_web_frontend_dom_PageInitializers$().initializeHeader__V();
    matchResult1: {
      var x1 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n($m_Lcom_brewery_web_frontend_Main$().Lcom_brewery_web_frontend_Main$__f_methods).get__O__s_Option(pathname);
      if ((x1 instanceof $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_s_Some)) {
        var x2 = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_s_Some(x1);
        var fn = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$as_F0($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(x2).s_Some__f_value);
        $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(fn).apply__O();
        window.console.log("Page Initialized");
        break matchResult1;
      }
      var x = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$m_s_None$();
      if ((x === x1)) {
        window.console.log("Route not found");
        break matchResult1;
      }
      throw new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$c_s_MatchError(x1);
    }
  }))));
});
var $d_Lcom_brewery_web_frontend_Main$ = new $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$TypeData().initClass($c_Lcom_brewery_web_frontend_Main$, "com.brewery.web.frontend.Main$", ({
  Lcom_brewery_web_frontend_Main$: 1
}));
export { $d_Lcom_brewery_web_frontend_Main$ as $d_Lcom_brewery_web_frontend_Main$ };
var $n_Lcom_brewery_web_frontend_Main$;
function $m_Lcom_brewery_web_frontend_Main$() {
  if ((!$n_Lcom_brewery_web_frontend_Main$)) {
    $n_Lcom_brewery_web_frontend_Main$ = new $c_Lcom_brewery_web_frontend_Main$();
  }
  return $n_Lcom_brewery_web_frontend_Main$;
}
export { $m_Lcom_brewery_web_frontend_Main$ as $m_Lcom_brewery_web_frontend_Main$ };
//# sourceMappingURL=com.brewery.web.frontend.-Main$.js.map
