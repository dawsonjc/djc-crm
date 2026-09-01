'use strict';
import * as $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6 from "./internal-3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.js";
var $b_Lcom_brewery_web_frontend_user_FormUser;
function $a_Lcom_brewery_web_frontend_user_FormUser() {
  if ((!$b_Lcom_brewery_web_frontend_user_FormUser)) {
    $b_Lcom_brewery_web_frontend_user_FormUser = class $b_Lcom_brewery_web_frontend_user_FormUser extends Object {
    constructor(...rest) {
      var userObject = null;
      userObject = ((rest[0] === (void 0)) ? null : rest[0]);
      super();
      Object.defineProperty(this, "username", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      Object.defineProperty(this, "password", ({
        "configurable": true,
        "enumerable": true,
        "writable": true,
        "value": null
      }));
      this.username = "";
      this.password = "";
      if ((userObject !== null)) {
        if ((userObject.username !== null)) {
          this.username = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(userObject.username));
        }
        if ((userObject.password !== null)) {
          this.password = $j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$dp_toString__T($j_internal$002d3ebfae0cba70adf981029a0da5b1e4b5ab5d02c6.$n(userObject.password));
        }
      }
    }
    };
  }
  return $b_Lcom_brewery_web_frontend_user_FormUser;
}
export { $a_Lcom_brewery_web_frontend_user_FormUser as $a_Lcom_brewery_web_frontend_user_FormUser };
//# sourceMappingURL=com.brewery.web.frontend.user.-Form-User.js.map
