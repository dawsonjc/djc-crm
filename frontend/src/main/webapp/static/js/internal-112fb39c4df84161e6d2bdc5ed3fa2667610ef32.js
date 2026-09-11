'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
function $is_ju_Map(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.ju_Map)));
}
export { $is_ju_Map as $is_ju_Map };
function $isArrayOf_ju_Map(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.ju_Map)));
}
export { $isArrayOf_ju_Map as $isArrayOf_ju_Map };
function $is_ju_Map$Entry(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.ju_Map$Entry)));
}
export { $is_ju_Map$Entry as $is_ju_Map$Entry };
function $isArrayOf_ju_Map$Entry(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.ju_Map$Entry)));
}
export { $isArrayOf_ju_Map$Entry as $isArrayOf_ju_Map$Entry };
/** @constructor */
function $c_ju_AbstractMap() {
}
export { $c_ju_AbstractMap as $c_ju_AbstractMap };
$c_ju_AbstractMap.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_ju_AbstractMap.prototype.constructor = $c_ju_AbstractMap;
/** @constructor */
function $h_ju_AbstractMap() {
}
export { $h_ju_AbstractMap as $h_ju_AbstractMap };
$h_ju_AbstractMap.prototype = $c_ju_AbstractMap.prototype;
$c_ju_AbstractMap.prototype.equals__O__Z = (function(o) {
  if ((o === this)) {
    return true;
  } else if ($is_ju_Map(o)) {
    if ((this.ju_HashMap__f_contentSize === o.ju_HashMap__f_contentSize)) {
      var _\uff3fself = new $c_ju_HashMap$EntrySet(this);
      var _\uff3fself$1 = _\uff3fself.iterator__ju_Iterator();
      _return: {
        while (_\uff3fself$1.hasNext__Z()) {
          var t = _\uff3fself$1.next__O();
          var key = t.getKey__O();
          var a = $p_ju_HashMap__getOrDefaultImpl__O__O__O(o, key, null);
          var b = t.getValue__O();
          if ((!((a === null) ? (b === null) : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_equals__O__Z(a, b)))) {
            var $x_1 = true;
            break _return;
          }
        }
        var $x_1 = false;
      }
      return (!$x_1);
    } else {
      return false;
    }
  } else {
    return false;
  }
});
$c_ju_AbstractMap.prototype.hashCode__I = (function() {
  var _\uff3fself = new $c_ju_HashMap$EntrySet(this);
  var _\uff3fself$1 = _\uff3fself.iterator__ju_Iterator();
  var result = 0;
  while (_\uff3fself$1.hasNext__Z()) {
    var t = result;
    var u = _\uff3fself$1.next__O();
    var t$1 = (t | 0);
    result = ((u.hashCode__I() + t$1) | 0);
  }
  return (result | 0);
});
$c_ju_AbstractMap.prototype.toString__T = (function() {
  var result = "{";
  var first = true;
  var iter = new $c_ju_HashMap$EntrySet(this).iterator__ju_Iterator();
  while (iter.hasNext__Z()) {
    var entry = iter.next__O();
    if (first) {
      first = false;
    } else {
      result = (result + ", ");
    }
    result = (((("" + result) + entry.getKey__O()) + "=") + entry.getValue__O());
  }
  return (result + "}");
});
function $is_ju_Collection(obj) {
  return (!(!((obj && obj.$classData) && obj.$classData.ancestors.ju_Collection)));
}
export { $is_ju_Collection as $is_ju_Collection };
function $isArrayOf_ju_Collection(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.ju_Collection)));
}
export { $isArrayOf_ju_Collection as $isArrayOf_ju_Collection };
function $ct_ju_HashMap$AbstractHashMapIterator__ju_HashMap__($thiz, outer) {
  $thiz.ju_HashMap$AbstractHashMapIterator__f_$outer = outer;
  $thiz.ju_HashMap$AbstractHashMapIterator__f_len = outer.ju_HashMap__f_java$util$HashMap$$table.u.length;
  return $thiz;
}
export { $ct_ju_HashMap$AbstractHashMapIterator__ju_HashMap__ as $ct_ju_HashMap$AbstractHashMapIterator__ju_HashMap__ };
/** @constructor */
function $c_ju_HashMap$AbstractHashMapIterator() {
  this.ju_HashMap$AbstractHashMapIterator__f_len = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextIdx = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextNode = null;
  this.ju_HashMap$AbstractHashMapIterator__f_$outer = null;
}
export { $c_ju_HashMap$AbstractHashMapIterator as $c_ju_HashMap$AbstractHashMapIterator };
$c_ju_HashMap$AbstractHashMapIterator.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_ju_HashMap$AbstractHashMapIterator.prototype.constructor = $c_ju_HashMap$AbstractHashMapIterator;
/** @constructor */
function $h_ju_HashMap$AbstractHashMapIterator() {
}
export { $h_ju_HashMap$AbstractHashMapIterator as $h_ju_HashMap$AbstractHashMapIterator };
$h_ju_HashMap$AbstractHashMapIterator.prototype = $c_ju_HashMap$AbstractHashMapIterator.prototype;
$c_ju_HashMap$AbstractHashMapIterator.prototype.hasNext__Z = (function() {
  if ((this.ju_HashMap$AbstractHashMapIterator__f_nextNode !== null)) {
    return true;
  } else {
    while ((this.ju_HashMap$AbstractHashMapIterator__f_nextIdx < this.ju_HashMap$AbstractHashMapIterator__f_len)) {
      var node = this.ju_HashMap$AbstractHashMapIterator__f_$outer.ju_HashMap__f_java$util$HashMap$$table.u[this.ju_HashMap$AbstractHashMapIterator__f_nextIdx];
      this.ju_HashMap$AbstractHashMapIterator__f_nextIdx = ((1 + this.ju_HashMap$AbstractHashMapIterator__f_nextIdx) | 0);
      if ((node !== null)) {
        this.ju_HashMap$AbstractHashMapIterator__f_nextNode = node;
        return true;
      }
    }
    return false;
  }
});
$c_ju_HashMap$AbstractHashMapIterator.prototype.next__O = (function() {
  if ((!this.hasNext__Z())) {
    throw new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_ju_NoSuchElementException("next on empty iterator");
  }
  var node = this.ju_HashMap$AbstractHashMapIterator__f_nextNode;
  this.ju_HashMap$AbstractHashMapIterator__f_nextNode = node.next__ju_HashMap$Node();
  return this.extract__ju_HashMap$Node__O(node);
});
var $d_ju_HashMap$Node = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass(0, "java.util.HashMap$Node", ({
  ju_HashMap$Node: 1,
  ju_Map$Entry: 1
}));
export { $d_ju_HashMap$Node as $d_ju_HashMap$Node };
function $ps_ju_UUID$__fail$1__T__E(name$1) {
  throw $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ct_jl_IllegalArgumentException__T__(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_IllegalArgumentException(), ("Invalid UUID string: " + name$1));
}
export { $ps_ju_UUID$__fail$1__T__E as $ps_ju_UUID$__fail$1__T__E };
/** @constructor */
function $c_ju_UUID$() {
}
export { $c_ju_UUID$ as $c_ju_UUID$ };
$c_ju_UUID$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_ju_UUID$.prototype.constructor = $c_ju_UUID$;
/** @constructor */
function $h_ju_UUID$() {
}
export { $h_ju_UUID$ as $h_ju_UUID$ };
$h_ju_UUID$.prototype = $c_ju_UUID$.prototype;
$c_ju_UUID$.prototype.fromString__T__ju_UUID = (function(name) {
  if ((((((name.length !== 36) || (name.charCodeAt(8) !== 45)) || (name.charCodeAt(13) !== 45)) || (name.charCodeAt(18) !== 45)) || (name.charCodeAt(23) !== 45))) {
    $ps_ju_UUID$__fail$1__T__E(name);
  }
  try {
    var his = name.substring(0, 4);
    var los = name.substring(4, 8);
    var this$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var $x_1 = this$1.java$lang$Integer$$parseIntImpl__T__I__I__I(his, 16, 134217728);
    var this$2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var i1 = (($x_1 << 16) | this$2.java$lang$Integer$$parseIntImpl__T__I__I__I(los, 16, 134217728));
    var his$1 = name.substring(9, 13);
    var los$1 = name.substring(14, 18);
    var this$3 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var $x_2 = this$3.java$lang$Integer$$parseIntImpl__T__I__I__I(his$1, 16, 134217728);
    var this$4 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var i2 = (($x_2 << 16) | this$4.java$lang$Integer$$parseIntImpl__T__I__I__I(los$1, 16, 134217728));
    var his$2 = name.substring(19, 23);
    var los$2 = name.substring(24, 28);
    var this$5 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var $x_3 = this$5.java$lang$Integer$$parseIntImpl__T__I__I__I(his$2, 16, 134217728);
    var this$6 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var i3 = (($x_3 << 16) | this$6.java$lang$Integer$$parseIntImpl__T__I__I__I(los$2, 16, 134217728));
    var his$3 = name.substring(28, 32);
    var los$3 = name.substring(32, 36);
    var this$7 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var $x_4 = this$7.java$lang$Integer$$parseIntImpl__T__I__I__I(his$3, 16, 134217728);
    var this$8 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_jl_Integer$();
    var i4 = (($x_4 << 16) | this$8.java$lang$Integer$$parseIntImpl__T__I__I__I(los$3, 16, 134217728));
    return new $c_ju_UUID(i1, i2, i3, i4);
  } catch (e) {
    if ((e instanceof $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_NumberFormatException)) {
      $ps_ju_UUID$__fail$1__T__E(name);
    } else {
      throw e;
    }
  }
});
var $d_ju_UUID$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_UUID$, "java.util.UUID$", ({
  ju_UUID$: 1,
  Ljava_io_Serializable: 1
}));
export { $d_ju_UUID$ as $d_ju_UUID$ };
var $n_ju_UUID$;
function $m_ju_UUID$() {
  if ((!$n_ju_UUID$)) {
    $n_ju_UUID$ = new $c_ju_UUID$();
  }
  return $n_ju_UUID$;
}
export { $m_ju_UUID$ as $m_ju_UUID$ };
/** @constructor */
function $c_ju_AbstractCollection() {
}
export { $c_ju_AbstractCollection as $c_ju_AbstractCollection };
$c_ju_AbstractCollection.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_ju_AbstractCollection.prototype.constructor = $c_ju_AbstractCollection;
/** @constructor */
function $h_ju_AbstractCollection() {
}
export { $h_ju_AbstractCollection as $h_ju_AbstractCollection };
$h_ju_AbstractCollection.prototype = $c_ju_AbstractCollection.prototype;
$c_ju_AbstractCollection.prototype.containsAll__ju_Collection__Z = (function(c) {
  var _\uff3fself = c.iterator__ju_Iterator();
  _return: {
    while (_\uff3fself.hasNext__Z()) {
      var t = _\uff3fself.next__O();
      if ((!this.contains__O__Z(t))) {
        var $x_1 = true;
        break _return;
      }
    }
    var $x_1 = false;
  }
  return (!$x_1);
});
$c_ju_AbstractCollection.prototype.toString__T = (function() {
  var _\uff3fself = this.iterator__ju_Iterator();
  var result = "[";
  var first = true;
  while (_\uff3fself.hasNext__Z()) {
    if (first) {
      first = false;
    } else {
      result = (result + ", ");
    }
    result = (("" + result) + _\uff3fself.next__O());
  }
  return (result + "]");
});
/** @constructor */
function $c_ju_HashMap$KeyIterator(outer) {
  this.ju_HashMap$AbstractHashMapIterator__f_len = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextIdx = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextNode = null;
  this.ju_HashMap$AbstractHashMapIterator__f_$outer = null;
  $ct_ju_HashMap$AbstractHashMapIterator__ju_HashMap__(this, outer);
}
export { $c_ju_HashMap$KeyIterator as $c_ju_HashMap$KeyIterator };
$c_ju_HashMap$KeyIterator.prototype = new $h_ju_HashMap$AbstractHashMapIterator();
$c_ju_HashMap$KeyIterator.prototype.constructor = $c_ju_HashMap$KeyIterator;
/** @constructor */
function $h_ju_HashMap$KeyIterator() {
}
export { $h_ju_HashMap$KeyIterator as $h_ju_HashMap$KeyIterator };
$h_ju_HashMap$KeyIterator.prototype = $c_ju_HashMap$KeyIterator.prototype;
$c_ju_HashMap$KeyIterator.prototype.extract__ju_HashMap$Node__O = (function(node) {
  return node.key__O();
});
var $d_ju_HashMap$KeyIterator = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashMap$KeyIterator, "java.util.HashMap$KeyIterator", ({
  ju_HashMap$KeyIterator: 1,
  ju_HashMap$AbstractHashMapIterator: 1,
  ju_Iterator: 1
}));
export { $d_ju_HashMap$KeyIterator as $d_ju_HashMap$KeyIterator };
/** @constructor */
function $c_ju_HashMap$NodeIterator(outer) {
  this.ju_HashMap$AbstractHashMapIterator__f_len = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextIdx = 0;
  this.ju_HashMap$AbstractHashMapIterator__f_nextNode = null;
  this.ju_HashMap$AbstractHashMapIterator__f_$outer = null;
  $ct_ju_HashMap$AbstractHashMapIterator__ju_HashMap__(this, outer);
}
export { $c_ju_HashMap$NodeIterator as $c_ju_HashMap$NodeIterator };
$c_ju_HashMap$NodeIterator.prototype = new $h_ju_HashMap$AbstractHashMapIterator();
$c_ju_HashMap$NodeIterator.prototype.constructor = $c_ju_HashMap$NodeIterator;
/** @constructor */
function $h_ju_HashMap$NodeIterator() {
}
export { $h_ju_HashMap$NodeIterator as $h_ju_HashMap$NodeIterator };
$h_ju_HashMap$NodeIterator.prototype = $c_ju_HashMap$NodeIterator.prototype;
$c_ju_HashMap$NodeIterator.prototype.extract__ju_HashMap$Node__O = (function(node) {
  return node;
});
var $d_ju_HashMap$NodeIterator = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashMap$NodeIterator, "java.util.HashMap$NodeIterator", ({
  ju_HashMap$NodeIterator: 1,
  ju_HashMap$AbstractHashMapIterator: 1,
  ju_Iterator: 1
}));
export { $d_ju_HashMap$NodeIterator as $d_ju_HashMap$NodeIterator };
/** @constructor */
function $c_ju_UUID(i1, i2, i3, i4) {
  this.ju_UUID__f_java$util$UUID$$i1 = 0;
  this.ju_UUID__f_java$util$UUID$$i2 = 0;
  this.ju_UUID__f_java$util$UUID$$i3 = 0;
  this.ju_UUID__f_java$util$UUID$$i4 = 0;
  this.ju_UUID__f_java$util$UUID$$i1 = i1;
  this.ju_UUID__f_java$util$UUID$$i2 = i2;
  this.ju_UUID__f_java$util$UUID$$i3 = i3;
  this.ju_UUID__f_java$util$UUID$$i4 = i4;
}
export { $c_ju_UUID as $c_ju_UUID };
$c_ju_UUID.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_ju_UUID.prototype.constructor = $c_ju_UUID;
/** @constructor */
function $h_ju_UUID() {
}
export { $h_ju_UUID as $h_ju_UUID };
$h_ju_UUID.prototype = $c_ju_UUID.prototype;
$c_ju_UUID.prototype.toString__T = (function() {
  var i = this.ju_UUID__f_java$util$UUID$$i1;
  var s = (i >>> 0.0).toString(16);
  var beginIndex = s.length;
  var $x_5 = "00000000".substring(beginIndex);
  var i$1 = ((this.ju_UUID__f_java$util$UUID$$i2 >>> 16) | 0);
  var s$1 = (i$1 >>> 0.0).toString(16);
  var beginIndex$1 = s$1.length;
  var $x_4 = "0000".substring(beginIndex$1);
  var i$2 = (65535 & this.ju_UUID__f_java$util$UUID$$i2);
  var s$2 = (i$2 >>> 0.0).toString(16);
  var beginIndex$2 = s$2.length;
  var $x_3 = "0000".substring(beginIndex$2);
  var i$3 = ((this.ju_UUID__f_java$util$UUID$$i3 >>> 16) | 0);
  var s$3 = (i$3 >>> 0.0).toString(16);
  var beginIndex$3 = s$3.length;
  var $x_2 = "0000".substring(beginIndex$3);
  var i$4 = (65535 & this.ju_UUID__f_java$util$UUID$$i3);
  var s$4 = (i$4 >>> 0.0).toString(16);
  var beginIndex$4 = s$4.length;
  var $x_1 = "0000".substring(beginIndex$4);
  var i$5 = this.ju_UUID__f_java$util$UUID$$i4;
  var s$5 = (i$5 >>> 0.0).toString(16);
  var beginIndex$5 = s$5.length;
  return ((((((((((("" + $x_5) + s) + "-") + (("" + $x_4) + s$1)) + "-") + (("" + $x_3) + s$2)) + "-") + (("" + $x_2) + s$3)) + "-") + (("" + $x_1) + s$4)) + (("" + "00000000".substring(beginIndex$5)) + s$5));
});
$c_ju_UUID.prototype.hashCode__I = (function() {
  return (((this.ju_UUID__f_java$util$UUID$$i1 ^ this.ju_UUID__f_java$util$UUID$$i2) ^ this.ju_UUID__f_java$util$UUID$$i3) ^ this.ju_UUID__f_java$util$UUID$$i4);
});
$c_ju_UUID.prototype.equals__O__Z = (function(that) {
  return ((that instanceof $c_ju_UUID) && ((((this.ju_UUID__f_java$util$UUID$$i1 === that.ju_UUID__f_java$util$UUID$$i1) && (this.ju_UUID__f_java$util$UUID$$i2 === that.ju_UUID__f_java$util$UUID$$i2)) && (this.ju_UUID__f_java$util$UUID$$i3 === that.ju_UUID__f_java$util$UUID$$i3)) && (this.ju_UUID__f_java$util$UUID$$i4 === that.ju_UUID__f_java$util$UUID$$i4)));
});
function $isArrayOf_ju_UUID(obj, depth) {
  return (!(!(((obj && obj.$classData) && (obj.$classData.arrayDepth === depth)) && obj.$classData.arrayBase.ancestors.ju_UUID)));
}
export { $isArrayOf_ju_UUID as $isArrayOf_ju_UUID };
var $d_ju_UUID = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_UUID, "java.util.UUID", ({
  ju_UUID: 1,
  Ljava_io_Serializable: 1,
  jl_Comparable: 1
}));
export { $d_ju_UUID as $d_ju_UUID };
/** @constructor */
function $c_ju_AbstractSet() {
}
export { $c_ju_AbstractSet as $c_ju_AbstractSet };
$c_ju_AbstractSet.prototype = new $h_ju_AbstractCollection();
$c_ju_AbstractSet.prototype.constructor = $c_ju_AbstractSet;
/** @constructor */
function $h_ju_AbstractSet() {
}
export { $h_ju_AbstractSet as $h_ju_AbstractSet };
$h_ju_AbstractSet.prototype = $c_ju_AbstractSet.prototype;
$c_ju_AbstractSet.prototype.equals__O__Z = (function(that) {
  return ((that === this) || ($is_ju_Collection(that) && ((that.size__I() === this.size__I()) && this.containsAll__ju_Collection__Z(that))));
});
$c_ju_AbstractSet.prototype.hashCode__I = (function() {
  var _\uff3fself = this.iterator__ju_Iterator();
  var result = 0;
  while (_\uff3fself.hasNext__Z()) {
    var t = result;
    var u = _\uff3fself.next__O();
    var t$1 = (t | 0);
    result = (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_hashCode__I(u) + t$1) | 0);
  }
  return (result | 0);
});
function $p_ju_HashMap__getOrDefaultImpl__O__O__O($thiz, key, defaultValue) {
  if ((key === null)) {
    var hash = 0;
  } else {
    var originalHash = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_hashCode__I(key);
    var hash = (originalHash ^ ((originalHash >>> 16) | 0));
  }
  var node = $thiz.java$util$HashMap$$findNode0__O__I__I__ju_HashMap$Node(key, hash, (hash & (((-1) + $thiz.ju_HashMap__f_java$util$HashMap$$table.u.length) | 0)));
  return ((node === null) ? defaultValue : node.value__O());
}
export { $p_ju_HashMap__getOrDefaultImpl__O__O__O as $p_ju_HashMap__getOrDefaultImpl__O__O__O };
function $ct_ju_HashMap__I__F__($thiz, initialCapacity, loadFactor) {
  $thiz.ju_HashMap__f_java$util$HashMap$$loadFactor = loadFactor;
  if ((initialCapacity < 0)) {
    throw $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ct_jl_IllegalArgumentException__T__(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_IllegalArgumentException(), "initialCapacity < 0");
  }
  if ((loadFactor <= 0.0)) {
    throw $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ct_jl_IllegalArgumentException__T__(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_jl_IllegalArgumentException(), "loadFactor <= 0.0");
  }
  var a = (((-1) + initialCapacity) | 0);
  var i = ((a > 4) ? a : 4);
  var a$1 = ((((-2147483648) >> Math.clz32(i)) & i) << 1);
  $thiz.ju_HashMap__f_java$util$HashMap$$table = new ($d_ju_HashMap$Node.getArrayOf().constr)(((a$1 < 1073741824) ? a$1 : 1073741824));
  $thiz.ju_HashMap__f_contentSize = 0;
  return $thiz;
}
export { $ct_ju_HashMap__I__F__ as $ct_ju_HashMap__I__F__ };
function $ct_ju_HashMap__($thiz) {
  $ct_ju_HashMap__I__F__($thiz, 16, 0.75);
  return $thiz;
}
export { $ct_ju_HashMap__ as $ct_ju_HashMap__ };
/** @constructor */
function $c_ju_HashMap() {
  this.ju_HashMap__f_java$util$HashMap$$loadFactor = 0.0;
  this.ju_HashMap__f_java$util$HashMap$$table = null;
  this.ju_HashMap__f_contentSize = 0;
}
export { $c_ju_HashMap as $c_ju_HashMap };
$c_ju_HashMap.prototype = new $h_ju_AbstractMap();
$c_ju_HashMap.prototype.constructor = $c_ju_HashMap;
/** @constructor */
function $h_ju_HashMap() {
}
export { $h_ju_HashMap as $h_ju_HashMap };
$h_ju_HashMap.prototype = $c_ju_HashMap.prototype;
$c_ju_HashMap.prototype.containsKey__O__Z = (function(key) {
  if ((key === null)) {
    var hash = 0;
  } else {
    var originalHash = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_hashCode__I(key);
    var hash = (originalHash ^ ((originalHash >>> 16) | 0));
  }
  return (this.java$util$HashMap$$findNode0__O__I__I__ju_HashMap$Node(key, hash, (hash & (((-1) + this.ju_HashMap__f_java$util$HashMap$$table.u.length) | 0))) !== null);
});
$c_ju_HashMap.prototype.java$util$HashMap$$findNode0__O__I__I__ju_HashMap$Node = (function(key, hash, idx) {
  var node = this.ju_HashMap__f_java$util$HashMap$$table.u[idx];
  while (true) {
    if ((node === null)) {
      return null;
    } else {
      if ((hash === node.hash__I())) {
        var b = node.key__O();
        var $x_1 = ((key === null) ? (b === null) : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_equals__O__Z(key, b));
      } else {
        var $x_1 = false;
      }
      if ($x_1) {
        return node;
      } else if ((hash < node.hash__I())) {
        return null;
      } else {
        node = node.next__ju_HashMap$Node();
      }
    }
  }
});
var $d_ju_HashMap = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashMap, "java.util.HashMap", ({
  ju_HashMap: 1,
  ju_AbstractMap: 1,
  ju_Map: 1,
  Ljava_io_Serializable: 1,
  jl_Cloneable: 1
}));
export { $d_ju_HashMap as $d_ju_HashMap };
/** @constructor */
function $c_ju_HashMap$EntrySet(outer) {
  this.ju_HashMap$EntrySet__f_$outer = null;
  this.ju_HashMap$EntrySet__f_$outer = outer;
}
export { $c_ju_HashMap$EntrySet as $c_ju_HashMap$EntrySet };
$c_ju_HashMap$EntrySet.prototype = new $h_ju_AbstractSet();
$c_ju_HashMap$EntrySet.prototype.constructor = $c_ju_HashMap$EntrySet;
/** @constructor */
function $h_ju_HashMap$EntrySet() {
}
export { $h_ju_HashMap$EntrySet as $h_ju_HashMap$EntrySet };
$h_ju_HashMap$EntrySet.prototype = $c_ju_HashMap$EntrySet.prototype;
$c_ju_HashMap$EntrySet.prototype.iterator__ju_Iterator = (function() {
  var this$1 = this.ju_HashMap$EntrySet__f_$outer;
  return new $c_ju_HashMap$NodeIterator(this$1);
});
$c_ju_HashMap$EntrySet.prototype.size__I = (function() {
  return this.ju_HashMap$EntrySet__f_$outer.ju_HashMap__f_contentSize;
});
$c_ju_HashMap$EntrySet.prototype.contains__O__Z = (function(o) {
  if ($is_ju_Map$Entry(o)) {
    var this$1 = this.ju_HashMap$EntrySet__f_$outer;
    var key = o.getKey__O();
    if ((key === null)) {
      var hash = 0;
    } else {
      var originalHash = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_hashCode__I(key);
      var hash = (originalHash ^ ((originalHash >>> 16) | 0));
    }
    var node = this$1.java$util$HashMap$$findNode0__O__I__I__ju_HashMap$Node(key, hash, (hash & (((-1) + this$1.ju_HashMap__f_java$util$HashMap$$table.u.length) | 0)));
    if ((node !== null)) {
      var a = node.getValue__O();
      var b = o.getValue__O();
      return ((a === null) ? (b === null) : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_equals__O__Z(a, b));
    } else {
      return false;
    }
  } else {
    return false;
  }
});
var $d_ju_HashMap$EntrySet = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashMap$EntrySet, "java.util.HashMap$EntrySet", ({
  ju_HashMap$EntrySet: 1,
  ju_AbstractSet: 1,
  ju_AbstractCollection: 1,
  ju_Collection: 1,
  jl_Iterable: 1,
  ju_Set: 1
}));
export { $d_ju_HashMap$EntrySet as $d_ju_HashMap$EntrySet };
/** @constructor */
function $c_ju_HashMap$KeySet(outer) {
  this.ju_HashMap$KeySet__f_$outer = null;
  this.ju_HashMap$KeySet__f_$outer = outer;
}
export { $c_ju_HashMap$KeySet as $c_ju_HashMap$KeySet };
$c_ju_HashMap$KeySet.prototype = new $h_ju_AbstractSet();
$c_ju_HashMap$KeySet.prototype.constructor = $c_ju_HashMap$KeySet;
/** @constructor */
function $h_ju_HashMap$KeySet() {
}
export { $h_ju_HashMap$KeySet as $h_ju_HashMap$KeySet };
$h_ju_HashMap$KeySet.prototype = $c_ju_HashMap$KeySet.prototype;
$c_ju_HashMap$KeySet.prototype.iterator__ju_Iterator = (function() {
  var this$1 = this.ju_HashMap$KeySet__f_$outer;
  return new $c_ju_HashMap$KeyIterator(this$1);
});
$c_ju_HashMap$KeySet.prototype.size__I = (function() {
  return this.ju_HashMap$KeySet__f_$outer.ju_HashMap__f_contentSize;
});
$c_ju_HashMap$KeySet.prototype.contains__O__Z = (function(o) {
  return this.ju_HashMap$KeySet__f_$outer.containsKey__O__Z(o);
});
var $d_ju_HashMap$KeySet = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashMap$KeySet, "java.util.HashMap$KeySet", ({
  ju_HashMap$KeySet: 1,
  ju_AbstractSet: 1,
  ju_AbstractCollection: 1,
  ju_Collection: 1,
  jl_Iterable: 1,
  ju_Set: 1
}));
export { $d_ju_HashMap$KeySet as $d_ju_HashMap$KeySet };
function $ct_ju_HashSet__ju_HashMap__($thiz, inner) {
  $thiz.ju_HashSet__f_inner = inner;
  $thiz.ju_HashSet__f_innerKeySet = new $c_ju_HashMap$KeySet(inner);
  return $thiz;
}
export { $ct_ju_HashSet__ju_HashMap__ as $ct_ju_HashSet__ju_HashMap__ };
function $ct_ju_HashSet__($thiz) {
  $ct_ju_HashSet__ju_HashMap__($thiz, $ct_ju_HashMap__(new $c_ju_HashMap()));
  return $thiz;
}
export { $ct_ju_HashSet__ as $ct_ju_HashSet__ };
/** @constructor */
function $c_ju_HashSet() {
  this.ju_HashSet__f_inner = null;
  this.ju_HashSet__f_innerKeySet = null;
}
export { $c_ju_HashSet as $c_ju_HashSet };
$c_ju_HashSet.prototype = new $h_ju_AbstractSet();
$c_ju_HashSet.prototype.constructor = $c_ju_HashSet;
/** @constructor */
function $h_ju_HashSet() {
}
export { $h_ju_HashSet as $h_ju_HashSet };
$h_ju_HashSet.prototype = $c_ju_HashSet.prototype;
$c_ju_HashSet.prototype.contains__O__Z = (function(o) {
  return this.ju_HashSet__f_inner.containsKey__O__Z(o);
});
$c_ju_HashSet.prototype.containsAll__ju_Collection__Z = (function(c) {
  return this.ju_HashSet__f_innerKeySet.containsAll__ju_Collection__Z(c);
});
$c_ju_HashSet.prototype.size__I = (function() {
  return this.ju_HashSet__f_inner.ju_HashMap__f_contentSize;
});
$c_ju_HashSet.prototype.iterator__ju_Iterator = (function() {
  return this.ju_HashSet__f_innerKeySet.iterator__ju_Iterator();
});
var $d_ju_HashSet = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_ju_HashSet, "java.util.HashSet", ({
  ju_HashSet: 1,
  ju_AbstractSet: 1,
  ju_AbstractCollection: 1,
  ju_Collection: 1,
  jl_Iterable: 1,
  ju_Set: 1,
  jl_Cloneable: 1,
  Ljava_io_Serializable: 1
}));
export { $d_ju_HashSet as $d_ju_HashSet };
//# sourceMappingURL=internal-112fb39c4df84161e6d2bdc5ed3fa2667610ef32.js.map
