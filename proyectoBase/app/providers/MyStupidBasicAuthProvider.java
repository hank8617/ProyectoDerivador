package providers;

import play.Application;

import play.twirl.api.Content;
import play.mvc.Http.Context;
import views.html.login;

import com.feth.play.module.pa.providers.wwwauth.basic.BasicAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import com.google.inject.Inject;

/** A really simple basic auth provider that accepts one hard coded user */
public class MyStupidBasicAuthProvider extends BasicAuthProvider {

	@Inject
	public MyStupidBasicAuthProvider(Application app) {
		super(app);
	}

	@Override
	protected AuthUser authenticateUser(String username, String password) {
		if (username.equals("example") && password.equals("secret")) {
			return new AuthUser() {
				private static final long serialVersionUID = 1L;

				@Override
				public String getId() {
					return "example";
				}

				@Override
				public String getProvider() {
					return "basic";
				}
			};
		}
		return null;
	}

	@Override
	public String getKey() {
		return "basic";
	}

	/** Diplay the normal login form if HTTP authentication fails */
	@Override
	protected Content unauthorized(Context context) {
		return login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM);
	}
}
