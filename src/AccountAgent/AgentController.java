package AccountAgent;

import controller.AbstractController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Account;
import model.Agent;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentController extends AbstractController {

    private Account account;

    public Button startAgentButton;
    public Button dismissButton;
    public Label agentLabel;
    public Label accountId;
    public Label accountName;
    public Label accountBalance;

    public void init(Account account, Agent.Type agentType) {
        if (agentType == Agent.Type.Deposit) {
            agentLabel.setText("Deposit Agent");
        } else {
            agentLabel.setText("Withdraw Agent");
        }

        setAccount(account);
    }

    public void dismissButtonHandler(ActionEvent actionEvent) {

    }

    public void startAgentHandler(ActionEvent actionEvent) {

    }

    private void setAccount(Account account) {
        this.account = account;

        accountId.setText(account.getID());
        accountName.setText(account.getName());
        accountBalance.setText(account.getStringBalance());

        account.getBalanceProperty().addListener((obs, oldBalance, newBalance) -> {
            if (newBalance != null) {
                accountBalance.setText(account.getStringBalance());
            }
        });
    }
}
