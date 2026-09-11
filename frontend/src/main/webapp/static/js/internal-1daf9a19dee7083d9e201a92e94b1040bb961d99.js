'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
function $ct_s_util_matching_Regex__ju_regex_Pattern__sci_Seq__($thiz, pattern, groupNames) {
  $thiz.s_util_matching_Regex__f_pattern = pattern;
  return $thiz;
}
export { $ct_s_util_matching_Regex__ju_regex_Pattern__sci_Seq__ as $ct_s_util_matching_Regex__ju_regex_Pattern__sci_Seq__ };
function $ct_s_util_matching_Regex__T__sci_Seq__($thiz, regex, groupNames) {
  $ct_s_util_matching_Regex__ju_regex_Pattern__sci_Seq__($thiz, $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_ju_regex_PatternCompiler$().compile__T__I__ju_regex_Pattern(regex, 0), groupNames);
  return $thiz;
}
export { $ct_s_util_matching_Regex__T__sci_Seq__ as $ct_s_util_matching_Regex__T__sci_Seq__ };
/** @constructor */
function $c_s_util_matching_Regex() {
  this.s_util_matching_Regex__f_pattern = null;
}
export { $c_s_util_matching_Regex as $c_s_util_matching_Regex };
$c_s_util_matching_Regex.prototype = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$h_O();
$c_s_util_matching_Regex.prototype.constructor = $c_s_util_matching_Regex;
/** @constructor */
function $h_s_util_matching_Regex() {
}
export { $h_s_util_matching_Regex as $h_s_util_matching_Regex };
$h_s_util_matching_Regex.prototype = $c_s_util_matching_Regex.prototype;
$c_s_util_matching_Regex.prototype.findFirstIn__jl_CharSequence__s_Option = (function(source) {
  var this$1 = this.s_util_matching_Regex__f_pattern;
  var m = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_ju_regex_Matcher(this$1, $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(source));
  return (m.find__Z() ? new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$c_s_Some(m.group__T()) : $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$m_s_None$());
});
$c_s_util_matching_Regex.prototype.toString__T = (function() {
  return this.s_util_matching_Regex__f_pattern.ju_regex_Pattern__f__pattern;
});
var $d_s_util_matching_Regex = new $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$TypeData().initClass($c_s_util_matching_Regex, "scala.util.matching.Regex", ({
  s_util_matching_Regex: 1,
  Ljava_io_Serializable: 1
}));
export { $d_s_util_matching_Regex as $d_s_util_matching_Regex };
//# sourceMappingURL=internal-1daf9a19dee7083d9e201a92e94b1040bb961d99.js.map
