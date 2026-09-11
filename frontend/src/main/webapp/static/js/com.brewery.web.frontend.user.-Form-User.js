'use strict';
import * as $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c from "./internal-07235935c771c9429268cb101d7f2e2e7f60988c.js";
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
          this.username = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(userObject.username);
        }
        if ((userObject.password !== null)) {
          this.password = $j_internal$002d07235935c771c9429268cb101d7f2e2e7f60988c.$dp_toString__T(userObject.password);
        }
      }
    }
    };
  }
  return $b_Lcom_brewery_web_frontend_user_FormUser;
}
export { $a_Lcom_brewery_web_frontend_user_FormUser as $a_Lcom_brewery_web_frontend_user_FormUser };
//# sourceMappingURL=com.brewery.web.frontend.user.-Form-User.js.map
