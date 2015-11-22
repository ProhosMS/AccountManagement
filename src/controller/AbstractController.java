package controller;

import model.AccountModel;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AbstractController implements Controller {

    protected AccountModel accountModel;

    @Override
    public void initModel(AccountModel model) {
        if (this.accountModel != null) {
            throw new IllegalStateException("Model can only be set once");
        }
        this.accountModel = model;
    }
}
