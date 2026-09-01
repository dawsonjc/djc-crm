'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_sc_ArrayOps$() {
  this.sc_ArrayOps$__f_fallback = null;
  $n_sc_ArrayOps$ = this;
  this.sc_ArrayOps$__f_fallback = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction1_$$Lambda$70e1780b84463d18653aacefee3ab989ac625f28(((x$1$2$2) => $m_sc_ArrayOps$().sc_ArrayOps$__f_fallback));
}
export { $c_sc_ArrayOps$ as $c_sc_ArrayOps$ };
$c_sc_ArrayOps$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sc_ArrayOps$.prototype.constructor = $c_sc_ArrayOps$;
/** @constructor */
function $h_sc_ArrayOps$() {
}
export { $h_sc_ArrayOps$ as $h_sc_ArrayOps$ };
$h_sc_ArrayOps$.prototype = $c_sc_ArrayOps$.prototype;
$c_sc_ArrayOps$.prototype.head$extension__O__O = (function(this$) {
  if (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_reflect_Array$().getLength__O__I(this$) !== 0)) {
    return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$().array_apply__O__I__O(this$, 0);
  } else {
    throw new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_ju_NoSuchElementException("head of empty array");
  }
});
var $d_sc_ArrayOps$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sc_ArrayOps$, "scala.collection.ArrayOps$", ({
  sc_ArrayOps$: 1
}));
export { $d_sc_ArrayOps$ as $d_sc_ArrayOps$ };
var $n_sc_ArrayOps$;
function $m_sc_ArrayOps$() {
  if ((!$n_sc_ArrayOps$)) {
    $n_sc_ArrayOps$ = new $c_sc_ArrayOps$();
  }
  return $n_sc_ArrayOps$;
}
export { $m_sc_ArrayOps$ as $m_sc_ArrayOps$ };
/** @constructor */
function $c_sci_Seq$() {
  this.sc_SeqFactory$Delegate__f_delegate = null;
  $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ct_sc_SeqFactory$Delegate__sc_SeqFactory__(this, $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sci_List$());
}
export { $c_sci_Seq$ as $c_sci_Seq$ };
$c_sci_Seq$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_sc_SeqFactory$Delegate();
$c_sci_Seq$.prototype.constructor = $c_sci_Seq$;
/** @constructor */
function $h_sci_Seq$() {
}
export { $h_sci_Seq$ as $h_sci_Seq$ };
$h_sci_Seq$.prototype = $c_sci_Seq$.prototype;
$c_sci_Seq$.prototype.from__sc_IterableOnce__sci_Seq = (function(it) {
  if ($is_sci_Seq(it)) {
    var x2 = $as_sci_Seq(it);
    return x2;
  } else {
    return $as_sci_Seq($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sc_SeqFactory$Delegate.prototype.from__sc_IterableOnce__sc_SeqOps.call(this, it));
  }
});
$c_sci_Seq$.prototype.from__sc_IterableOnce__O = (function(source) {
  return this.from__sc_IterableOnce__sci_Seq(source);
});
$c_sci_Seq$.prototype.from__sc_IterableOnce__sc_SeqOps = (function(it) {
  return this.from__sc_IterableOnce__sci_Seq(it);
});
var $d_sci_Seq$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sci_Seq$, "scala.collection.immutable.Seq$", ({
  sci_Seq$: 1,
  sc_SeqFactory$Delegate: 1,
  sc_SeqFactory: 1,
  sc_IterableFactory: 1,
  Ljava_io_Serializable: 1
}));
export { $d_sci_Seq$ as $d_sci_Seq$ };
var $n_sci_Seq$;
function $m_sci_Seq$() {
  if ((!$n_sci_Seq$)) {
    $n_sci_Seq$ = new $c_sci_Seq$();
  }
  return $n_sci_Seq$;
}
export { $m_sci_Seq$ as $m_sci_Seq$ };
function $is_sci_Seq(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.sci_Seq)));
}
export { $is_sci_Seq as $is_sci_Seq };
function $as_sci_Seq(obj) {
  return (($is_sci_Seq(obj) || (obj === null)) ? obj : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$throwClassCastException(obj, "scala.collection.immutable.Seq"));
}
export { $as_sci_Seq as $as_sci_Seq };
function $isArrayOf_sci_Seq(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.sci_Seq)));
}
export { $isArrayOf_sci_Seq as $isArrayOf_sci_Seq };
function $asArrayOf_sci_Seq(obj, depth) {
  return (($isArrayOf_sci_Seq(obj, depth) || (obj === null)) ? obj : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$throwArrayCastException(obj, "Lscala.collection.immutable.Seq;", depth));
}
export { $asArrayOf_sci_Seq as $asArrayOf_sci_Seq };
//# sourceMappingURL=internal-37cfa26efaf23e1af114c96ef59fd12c792af46c.js.map
