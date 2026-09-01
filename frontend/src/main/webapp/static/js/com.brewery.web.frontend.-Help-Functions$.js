'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
import * as $j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c from "./internal-37cfa26efaf23e1af114c96ef59fd12c792af46c.js";
/** @constructor */
function $c_Lcom_brewery_web_frontend_HelpFunctions$() {
}
export { $c_Lcom_brewery_web_frontend_HelpFunctions$ as $c_Lcom_brewery_web_frontend_HelpFunctions$ };
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.constructor = $c_Lcom_brewery_web_frontend_HelpFunctions$;
/** @constructor */
function $h_Lcom_brewery_web_frontend_HelpFunctions$() {
}
export { $h_Lcom_brewery_web_frontend_HelpFunctions$ as $h_Lcom_brewery_web_frontend_HelpFunctions$ };
$h_Lcom_brewery_web_frontend_HelpFunctions$.prototype = $c_Lcom_brewery_web_frontend_HelpFunctions$.prototype;
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.getFormData__Lio_udash_wrappers_jquery_JQuery__sci_Map = (function(form) {
  var unindexedArray = form.serializeArray();
  var this$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sci_Map$();
  var elems = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([]));
  var elem = this$1.from__sc_IterableOnce__sci_Map(elems);
  var elem$1 = null;
  elem$1 = elem;
  var end = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uI(unindexedArray.length);
  var isEmpty = (end <= 0);
  var scala$collection$immutable$Range$$lastElement = (((-1) + end) | 0);
  if ((!isEmpty)) {
    var i = 0;
    while (true) {
      var x0 = i;
      var obj = unindexedArray[x0];
      var name = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(obj.name));
      var value = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(obj.value));
      if ((!$j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).contains__O__Z(name))) {
        var this$9 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1));
        elem$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(this$9.updated__O__O__sci_MapOps(name, value));
      } else if (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).contains__O__Z(name) && (!$j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c.$is_sci_Seq($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).apply__O__O(name))))) {
        var this$12 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1));
        var y = $j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c.$m_sci_Seq$().apply__sci_Seq__sc_SeqOps($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$().genericWrapArray__O__sci_ArraySeq(new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$ac_O([$j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).apply__O__O(name), value])));
        elem$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(this$12.updated__O__O__sci_MapOps(name, y));
      } else if (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).contains__O__Z(name) && $j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c.$is_sci_Seq($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).apply__O__O(name)))) {
        var this$15 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1));
        var y$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c.$as_sci_Seq($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1)).apply__O__O(name))).appended__O__O(value);
        elem$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(this$15.updated__O__O__sci_MapOps(name, y$1));
      }
      if ((i === scala$collection$immutable$Range$$lastElement)) {
        break;
      }
      i = ((1 + i) | 0);
    }
  }
  return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_sci_Map(elem$1);
});
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.cleanseString__T__T = (function(string) {
  var retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(string), "<", "&lt;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), ">", "&gt;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), "\"", "&#34;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), "'", "&#39;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), "/", "&#x2F;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), "\\\\", "&#x5C;");
  retString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(retString), "`", "&#96;");
  return retString;
});
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.formatDate__sjs_js_Date__T = (function(date) {
  var $x_1 = Intl.DateTimeFormat;
  var fields = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("year", "numeric"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("month", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("day", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("hour", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("minute", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("hour12", true)]));
  var dt = new $x_1("en-US", $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields));
  var dateFormatted = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(dt.format(date));
  return dateFormatted;
});
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.parseToUTCDateTime__sjs_js_Date__T = (function(date) {
  var fields = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sr_ScalaRunTime$().wrapRefArray__AO__sci_ArraySeq(new ($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$d_T2.getArrayOf().constr)([new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("timeZone", "UTC"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("year", "numeric"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("month", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("day", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("hour", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("minute", "2-digit"), new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_T2("hour12", true)]));
  var options = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_sjs_js_special_package$().objectLiteral__sci_Seq__sjs_js_Object(fields);
  var formatter = Intl.DateTimeFormat("en-US", options);
  return $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__replaceAll__T__T__T($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(formatter.format(date))), ",", "");
});
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.setCookie__T__sjs_js_Any__s_Option__V = (function(name, obj, days) {
  var jsonString = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(JSON.stringify(obj));
  var encodedValue = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(encodeURIComponent(jsonString));
  matchResult1: {
    var expires;
    if ((days instanceof $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_s_Some)) {
      var x2 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_s_Some(days);
      var d = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uI($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(x2).s_Some__f_value);
      var date = new Date();
      date.setTime(($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$uD(date.getTime()) + Math.imul(1000, Math.imul(60, Math.imul(60, Math.imul(24, d))))));
      var expires = ("; expires=" + $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(date.toUTCString()));
      break matchResult1;
    }
    var x = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_s_None$();
    if ((x === days)) {
      var expires = "";
      break matchResult1;
    }
    throw new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_s_MatchError(days);
  }
  document.cookie = ((((name + "=") + encodedValue) + expires) + "; path=/");
});
$c_Lcom_brewery_web_frontend_HelpFunctions$.prototype.getCookie__T__sjs_js_Dynamic = (function(name) {
  var value = ("; " + $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T(document.cookie));
  var regex = (("; " + name) + "=");
  var parts = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__split__T__I__AT(value, regex, 0);
  if (($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(parts).u.length === 2)) {
    var $x_3 = JSON;
    var $x_2 = decodeURIComponent;
    var this$1 = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$n(parts).get(1));
    var xs = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$f_T__split__T__I__AT(this$1, ";", 0);
    var $x_1 = $x_2($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T($j_internal$002d37cfa26efaf23e1af114c96ef59fd12c792af46c.$m_sc_ArrayOps$().head$extension__O__O(xs)));
    return $x_3.parse($j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$as_T($x_1));
  }
  return null;
});
var $d_Lcom_brewery_web_frontend_HelpFunctions$ = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_Lcom_brewery_web_frontend_HelpFunctions$, "com.brewery.web.frontend.HelpFunctions$", ({
  Lcom_brewery_web_frontend_HelpFunctions$: 1
}));
export { $d_Lcom_brewery_web_frontend_HelpFunctions$ as $d_Lcom_brewery_web_frontend_HelpFunctions$ };
var $n_Lcom_brewery_web_frontend_HelpFunctions$;
function $m_Lcom_brewery_web_frontend_HelpFunctions$() {
  if ((!$n_Lcom_brewery_web_frontend_HelpFunctions$)) {
    $n_Lcom_brewery_web_frontend_HelpFunctions$ = new $c_Lcom_brewery_web_frontend_HelpFunctions$();
  }
  return $n_Lcom_brewery_web_frontend_HelpFunctions$;
}
export { $m_Lcom_brewery_web_frontend_HelpFunctions$ as $m_Lcom_brewery_web_frontend_HelpFunctions$ };
//# sourceMappingURL=com.brewery.web.frontend.-Help-Functions$.js.map
