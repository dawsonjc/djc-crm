'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
/** @constructor */
function $c_sc_StringOps$() {
  this.sc_StringOps$__f_fallback = null;
  $n_sc_StringOps$ = this;
  this.sc_StringOps$__f_fallback = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction1_$$Lambda$70e1780b84463d18653aacefee3ab989ac625f28(((x$1$2$2) => $m_sc_StringOps$().sc_StringOps$__f_fallback));
}
export { $c_sc_StringOps$ as $c_sc_StringOps$ };
$c_sc_StringOps$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sc_StringOps$.prototype.constructor = $c_sc_StringOps$;
/** @constructor */
function $h_sc_StringOps$() {
}
export { $h_sc_StringOps$ as $h_sc_StringOps$ };
$h_sc_StringOps$.prototype = $c_sc_StringOps$.prototype;
$c_sc_StringOps$.prototype.stripMargin$extension__T__C__T = (function(this$, marginChar) {
  var this$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(this$);
  var sb = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ct_jl_StringBuilder__I__(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_StringBuilder(), this$1.length);
  var this$4 = new $c_sc_StringOps$$anon$1(this$, false);
  while ((this$4.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index < this$4.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len)) {
    var x0 = this$4.next__T();
    var this$5 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x0);
    var len = this$5.length;
    var index = 0;
    while (true) {
      if ((index < len)) {
        var this$6 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x0);
        var index$1 = index;
        var $x_1 = ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$6, index$1) <= 32);
      } else {
        var $x_1 = false;
      }
      if ($x_1) {
        index = ((1 + index) | 0);
      } else {
        break;
      }
    }
    if ((index < len)) {
      var this$7 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x0);
      var index$2 = index;
      var $x_2 = ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$7, index$2) === marginChar);
    } else {
      var $x_2 = false;
    }
    if ($x_2) {
      var this$8 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x0);
      var beginIndex = ((1 + index) | 0);
      if (((beginIndex < 0) || (beginIndex > this$8.length))) {
        $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$8, beginIndex);
      }
      var stripped = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(this$8.substring(beginIndex));
    } else {
      var stripped = x0;
    }
    sb.jl_StringBuilder__f_java$lang$StringBuilder$$content = (("" + sb.jl_StringBuilder__f_java$lang$StringBuilder$$content) + stripped);
  }
  return sb.jl_StringBuilder__f_java$lang$StringBuilder$$content;
});
$c_sc_StringOps$.prototype.unwrapArg$extension__T__O__O = (function(this$, arg) {
  if (false) {
    var x2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_s_math_ScalaNumber(arg);
    return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x2).underlying__O();
  } else {
    return arg;
  }
});
$c_sc_StringOps$.prototype.format$extension__T__sci_Seq__T = (function(this$, args) {
  var args$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$asArrayOf_O($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sc_IterableOnceOps($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(args).map__F1__O(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_sr_AbstractFunction1_$$Lambda$70e1780b84463d18653aacefee3ab989ac625f28(((arg$2$2) => $m_sc_StringOps$().unwrapArg$extension__T__O__O(this$, arg$2$2)))))).toArray__s_reflect_ClassTag__O($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_s_reflect_ManifestFactory$ObjectManifest$()), 1);
  return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_String$().format__T__AO__T(this$, args$1);
});
var $d_sc_StringOps$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sc_StringOps$, "scala.collection.StringOps$", ({
  sc_StringOps$: 1
}));
export { $d_sc_StringOps$ as $d_sc_StringOps$ };
var $n_sc_StringOps$;
function $m_sc_StringOps$() {
  if ((!$n_sc_StringOps$)) {
    $n_sc_StringOps$ = new $c_sc_StringOps$();
  }
  return $n_sc_StringOps$;
}
export { $m_sc_StringOps$ as $m_sc_StringOps$ };
function $p_s_concurrent_ExecutionContext$__global$lzycompute__s_concurrent_ExecutionContextExecutor($thiz) {
  if ((!$thiz.s_concurrent_ExecutionContext$__f_bitmap$0)) {
    $thiz.s_concurrent_ExecutionContext$__f_global = $m_sjs_concurrent_JSExecutionContext$().sjs_concurrent_JSExecutionContext$__f_queue;
    $thiz.s_concurrent_ExecutionContext$__f_bitmap$0 = true;
  }
  return $thiz.s_concurrent_ExecutionContext$__f_global;
}
export { $p_s_concurrent_ExecutionContext$__global$lzycompute__s_concurrent_ExecutionContextExecutor as $p_s_concurrent_ExecutionContext$__global$lzycompute__s_concurrent_ExecutionContextExecutor };
/** @constructor */
function $c_s_concurrent_ExecutionContext$() {
  this.s_concurrent_ExecutionContext$__f_global = null;
  this.s_concurrent_ExecutionContext$__f_bitmap$0 = false;
}
export { $c_s_concurrent_ExecutionContext$ as $c_s_concurrent_ExecutionContext$ };
$c_s_concurrent_ExecutionContext$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_s_concurrent_ExecutionContext$.prototype.constructor = $c_s_concurrent_ExecutionContext$;
/** @constructor */
function $h_s_concurrent_ExecutionContext$() {
}
export { $h_s_concurrent_ExecutionContext$ as $h_s_concurrent_ExecutionContext$ };
$h_s_concurrent_ExecutionContext$.prototype = $c_s_concurrent_ExecutionContext$.prototype;
$c_s_concurrent_ExecutionContext$.prototype.global__s_concurrent_ExecutionContextExecutor = (function() {
  return ((!this.s_concurrent_ExecutionContext$__f_bitmap$0) ? $p_s_concurrent_ExecutionContext$__global$lzycompute__s_concurrent_ExecutionContextExecutor(this) : this.s_concurrent_ExecutionContext$__f_global);
});
var $d_s_concurrent_ExecutionContext$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_s_concurrent_ExecutionContext$, "scala.concurrent.ExecutionContext$", ({
  s_concurrent_ExecutionContext$: 1
}));
export { $d_s_concurrent_ExecutionContext$ as $d_s_concurrent_ExecutionContext$ };
var $n_s_concurrent_ExecutionContext$;
function $m_s_concurrent_ExecutionContext$() {
  if ((!$n_s_concurrent_ExecutionContext$)) {
    $n_s_concurrent_ExecutionContext$ = new $c_s_concurrent_ExecutionContext$();
  }
  return $n_s_concurrent_ExecutionContext$;
}
export { $m_s_concurrent_ExecutionContext$ as $m_s_concurrent_ExecutionContext$ };
/** @constructor */
function $c_sjs_concurrent_JSExecutionContext$() {
  this.sjs_concurrent_JSExecutionContext$__f_queue = null;
  $n_sjs_concurrent_JSExecutionContext$ = this;
  this.sjs_concurrent_JSExecutionContext$__f_queue = $m_sjs_concurrent_QueueExecutionContext$().apply__s_concurrent_ExecutionContextExecutor();
}
export { $c_sjs_concurrent_JSExecutionContext$ as $c_sjs_concurrent_JSExecutionContext$ };
$c_sjs_concurrent_JSExecutionContext$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sjs_concurrent_JSExecutionContext$.prototype.constructor = $c_sjs_concurrent_JSExecutionContext$;
/** @constructor */
function $h_sjs_concurrent_JSExecutionContext$() {
}
export { $h_sjs_concurrent_JSExecutionContext$ as $h_sjs_concurrent_JSExecutionContext$ };
$h_sjs_concurrent_JSExecutionContext$.prototype = $c_sjs_concurrent_JSExecutionContext$.prototype;
var $d_sjs_concurrent_JSExecutionContext$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sjs_concurrent_JSExecutionContext$, "scala.scalajs.concurrent.JSExecutionContext$", ({
  sjs_concurrent_JSExecutionContext$: 1
}));
export { $d_sjs_concurrent_JSExecutionContext$ as $d_sjs_concurrent_JSExecutionContext$ };
var $n_sjs_concurrent_JSExecutionContext$;
function $m_sjs_concurrent_JSExecutionContext$() {
  if ((!$n_sjs_concurrent_JSExecutionContext$)) {
    $n_sjs_concurrent_JSExecutionContext$ = new $c_sjs_concurrent_JSExecutionContext$();
  }
  return $n_sjs_concurrent_JSExecutionContext$;
}
export { $m_sjs_concurrent_JSExecutionContext$ as $m_sjs_concurrent_JSExecutionContext$ };
/** @constructor */
function $c_sjs_concurrent_QueueExecutionContext$() {
}
export { $c_sjs_concurrent_QueueExecutionContext$ as $c_sjs_concurrent_QueueExecutionContext$ };
$c_sjs_concurrent_QueueExecutionContext$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sjs_concurrent_QueueExecutionContext$.prototype.constructor = $c_sjs_concurrent_QueueExecutionContext$;
/** @constructor */
function $h_sjs_concurrent_QueueExecutionContext$() {
}
export { $h_sjs_concurrent_QueueExecutionContext$ as $h_sjs_concurrent_QueueExecutionContext$ };
$h_sjs_concurrent_QueueExecutionContext$.prototype = $c_sjs_concurrent_QueueExecutionContext$.prototype;
$c_sjs_concurrent_QueueExecutionContext$.prototype.apply__s_concurrent_ExecutionContextExecutor = (function() {
  return (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T((typeof Promise)) === "undefined") ? new $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext() : new $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext());
});
var $d_sjs_concurrent_QueueExecutionContext$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sjs_concurrent_QueueExecutionContext$, "scala.scalajs.concurrent.QueueExecutionContext$", ({
  sjs_concurrent_QueueExecutionContext$: 1
}));
export { $d_sjs_concurrent_QueueExecutionContext$ as $d_sjs_concurrent_QueueExecutionContext$ };
var $n_sjs_concurrent_QueueExecutionContext$;
function $m_sjs_concurrent_QueueExecutionContext$() {
  if ((!$n_sjs_concurrent_QueueExecutionContext$)) {
    $n_sjs_concurrent_QueueExecutionContext$ = new $c_sjs_concurrent_QueueExecutionContext$();
  }
  return $n_sjs_concurrent_QueueExecutionContext$;
}
export { $m_sjs_concurrent_QueueExecutionContext$ as $m_sjs_concurrent_QueueExecutionContext$ };
/** @constructor */
function $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext() {
  Promise.resolve((void 0));
}
export { $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext as $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext };
$c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext.prototype.constructor = $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext;
/** @constructor */
function $h_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext() {
}
export { $h_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext as $h_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext };
$h_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext.prototype = $c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext.prototype;
var $d_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext, "scala.scalajs.concurrent.QueueExecutionContext$PromisesExecutionContext", ({
  sjs_concurrent_QueueExecutionContext$PromisesExecutionContext: 1,
  s_concurrent_ExecutionContextExecutor: 1,
  s_concurrent_ExecutionContext: 1,
  ju_concurrent_Executor: 1
}));
export { $d_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext as $d_sjs_concurrent_QueueExecutionContext$PromisesExecutionContext };
/** @constructor */
function $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext() {
}
export { $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext as $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext };
$c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext.prototype.constructor = $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext;
/** @constructor */
function $h_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext() {
}
export { $h_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext as $h_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext };
$h_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext.prototype = $c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext.prototype;
var $d_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext, "scala.scalajs.concurrent.QueueExecutionContext$TimeoutsExecutionContext", ({
  sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext: 1,
  s_concurrent_ExecutionContextExecutor: 1,
  s_concurrent_ExecutionContext: 1,
  ju_concurrent_Executor: 1
}));
export { $d_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext as $d_sjs_concurrent_QueueExecutionContext$TimeoutsExecutionContext };
function $p_sc_StringOps$$anon$1__advance__T($thiz) {
  var start = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
  while (true) {
    if (($thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index < $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len)) {
      var this$ = $thiz.sc_StringOps$$anon$1__f_$this$2;
      var i = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
      var this$2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(this$);
      var c = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$2, i);
      var $x_1 = (!((c === 13) || (c === 10)));
    } else {
      var $x_1 = false;
    }
    if ($x_1) {
      $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index = ((1 + $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index) | 0);
    } else {
      break;
    }
  }
  var end = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
  if (($thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index < $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len)) {
    var this$$2 = $thiz.sc_StringOps$$anon$1__f_$this$2;
    var i$1 = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
    var this$5 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(this$$2);
    var c$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$5, i$1);
    $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index = ((1 + $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index) | 0);
    if (($thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index < $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len)) {
      var this$$3 = $thiz.sc_StringOps$$anon$1__f_$this$2;
      var i$2 = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
      var this$7 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(this$$3);
      var c$2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$7, i$2);
      var $x_2 = ((c$1 === 13) && (c$2 === 10));
    } else {
      var $x_2 = false;
    }
    if ($x_2) {
      $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index = ((1 + $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index) | 0);
    }
    if ((!$thiz.sc_StringOps$$anon$1__f_stripped$1)) {
      end = $thiz.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index;
    }
  }
  var this$9 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($thiz.sc_StringOps$$anon$1__f_$this$2);
  var endIndex = end;
  if ((start < 0)) {
    $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$9, start);
  }
  if ((endIndex > this$9.length)) {
    $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$9, endIndex);
  }
  if ((endIndex < start)) {
    $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$charAt(this$9, (-1));
  }
  return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(this$9.substring(start, endIndex));
}
export { $p_sc_StringOps$$anon$1__advance__T as $p_sc_StringOps$$anon$1__advance__T };
/** @constructor */
function $c_sc_StringOps$$anon$1(\u03b4this$2, stripped$1) {
  this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len = 0;
  this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index = 0;
  this.sc_StringOps$$anon$1__f_$this$2 = null;
  this.sc_StringOps$$anon$1__f_stripped$1 = false;
  this.sc_StringOps$$anon$1__f_$this$2 = \u03b4this$2;
  this.sc_StringOps$$anon$1__f_stripped$1 = stripped$1;
  var this$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(\u03b4this$2);
  this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len = this$1.length;
  this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index = 0;
}
export { $c_sc_StringOps$$anon$1 as $c_sc_StringOps$$anon$1 };
$c_sc_StringOps$$anon$1.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_sc_AbstractIterator();
$c_sc_StringOps$$anon$1.prototype.constructor = $c_sc_StringOps$$anon$1;
/** @constructor */
function $h_sc_StringOps$$anon$1() {
}
export { $h_sc_StringOps$$anon$1 as $h_sc_StringOps$$anon$1 };
$h_sc_StringOps$$anon$1.prototype = $c_sc_StringOps$$anon$1.prototype;
$c_sc_StringOps$$anon$1.prototype.hasNext__Z = (function() {
  return (this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index < this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len);
});
$c_sc_StringOps$$anon$1.prototype.next__T = (function() {
  return ((this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$index >= this.sc_StringOps$$anon$1__f_scala$collection$StringOps$$anon$$len) ? $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sc_Iterator$().sc_Iterator$__f_scala$collection$Iterator$$_empty).next__O()) : $p_sc_StringOps$$anon$1__advance__T(this));
});
$c_sc_StringOps$$anon$1.prototype.next__O = (function() {
  return this.next__T();
});
var $d_sc_StringOps$$anon$1 = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_sc_StringOps$$anon$1, "scala.collection.StringOps$$anon$1", ({
  sc_StringOps$$anon$1: 1,
  sc_AbstractIterator: 1,
  sc_Iterator: 1,
  sc_IterableOnce: 1,
  sc_IterableOnceOps: 1
}));
export { $d_sc_StringOps$$anon$1 as $d_sc_StringOps$$anon$1 };
//# sourceMappingURL=internal-f33bfacc298d8dab923476fe9af133b88098a05b.js.map
