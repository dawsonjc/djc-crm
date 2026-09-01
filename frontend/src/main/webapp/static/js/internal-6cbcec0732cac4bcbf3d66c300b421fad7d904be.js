'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_sjs_js_ArrayOps$() {
}
export { $c_sjs_js_ArrayOps$ as $c_sjs_js_ArrayOps$ };
$c_sjs_js_ArrayOps$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sjs_js_ArrayOps$.prototype.constructor = $c_sjs_js_ArrayOps$;
/** @constructor */
function $h_sjs_js_ArrayOps$() {
}
export { $h_sjs_js_ArrayOps$ as $h_sjs_js_ArrayOps$ };
$h_sjs_js_ArrayOps$.prototype = $c_sjs_js_ArrayOps$.prototype;
$c_sjs_js_ArrayOps$.prototype.indexOf$extension__sjs_js_Array__O__I__I = (function(this$, elem, from) {
  var len = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uI(this$.length);
  var i = from;
  while ((i < len)) {
    var x0 = this$[i];
    if ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_BoxesRunTime$().equals__O__O__Z(elem, x0)) {
      return i;
    }
    i = ((1 + i) | 0);
  }
  return (-1);
});
$c_sjs_js_ArrayOps$.prototype.contains$extension__sjs_js_Array__O__Z = (function(this$, elem) {
  return ($m_sjs_js_ArrayOps$().indexOf$extension__sjs_js_Array__O__I__I(this$, elem, 0) >= 0);
});
var $d_sjs_js_ArrayOps$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sjs_js_ArrayOps$, "scala.scalajs.js.ArrayOps$", ({
  sjs_js_ArrayOps$: 1
}));
export { $d_sjs_js_ArrayOps$ as $d_sjs_js_ArrayOps$ };
var $n_sjs_js_ArrayOps$;
function $m_sjs_js_ArrayOps$() {
  if ((!$n_sjs_js_ArrayOps$)) {
    $n_sjs_js_ArrayOps$ = new $c_sjs_js_ArrayOps$();
  }
  return $n_sjs_js_ArrayOps$;
}
export { $m_sjs_js_ArrayOps$ as $m_sjs_js_ArrayOps$ };
//# sourceMappingURL=internal-6cbcec0732cac4bcbf3d66c300b421fad7d904be.js.map
