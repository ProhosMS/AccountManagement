package AccountAgent;

import controller.AbstractController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import model.Account.Account;
import model.Agent.Agent;
import model.Agent.DepositAgent;
import model.Agent.WithdrawAgent;
import util.AccountUtil;
import util.StageUtil;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class AgentController extends AbstractController {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^(?:0|[1-9\\.]\\d{0,2}(?:,?\\d{3})*)(?:\\.\\d+)?$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d+(?:\\.\\d+)?$");
    private static final Pattern FIX_NUMBER_PATTERN = Pattern.compile("[^\\d+\\.]+");

    private AgentThreadMonitor agentThreadMonitor;

    private Account account;
    private Agent.Type agentType;
    private boolean validTransferField;
    private boolean validTimeIntervalField;

    public TextField timeIntervalField;
    public TextField transferField;
    public Button startAgentButton;
    public Button dismissButton;
    public Label agentLabel;
    public Label accountId;
    public Label accountName;
    public Label accountBalance;

    public void init(Account account, Agent.Type agentType) {
        this.agentType = agentType;
        if (agentType == Agent.Type.Deposit) {
            agentLabel.setText("Deposit Agent");
        } else {
            agentLabel.setText("Withdraw Agent");
        }

        agentThreadMonitor = AgentThreadMonitor.getInstance();
        setAccount(account);
        prepareFields();
    }

    public void dismissButtonHandler() {
        Stage stage = (Stage) dismissButton.getScene().getWindow();
        stage.close();
    }

    public void startAgentHandler(ActionEvent actionEvent) throws IOException {
        String transferString = FIX_NUMBER_PATTERN.matcher(transferField.getText()).replaceAll("");
        Double transferAmount = Double.parseDouble(transferString);
        long timeInterval = (long)Double.parseDouble(timeIntervalField.getText());
        Agent agent;

        /* keep track of withdraw/deposit agent, if withdraw agent exist, try to unblock */

        if (agentType == Agent.Type.Deposit) {
            agent = new DepositAgent(account, agentType, timeInterval, transferAmount);
        } else {
            agent = new WithdrawAgent(account, agentType, timeInterval, transferAmount);
        }

        DefinedAgentView definedAgentView = new DefinedAgentView();
        DefinedAgentController definedAgentController = definedAgentView.getController();
        definedAgentController.init(agent);

        Stage stage = StageUtil.initStage(definedAgentView, 500, 300);
        stage.show();

        dismissButtonHandler();
    }

    private void setAccount(Account account) {
        this.account = account;

        accountId.setText(account.getID());
        accountName.setText(account.getName());
        accountBalance.setText(AccountUtil.getStringBalance(account.getBalance()));

        account.getBalanceProperty().addListener((obs, oldBalance, newBalance) -> {
            if (newBalance != null) {
                accountBalance.setText(AccountUtil.getStringBalance(account.getBalance()));
            }
        });
    }

    private void prepareFields() {
        timeIntervalField.setText("0.0");
        startAgentButton.setDisable(true);

        timeIntervalField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (verifyTimeInterval() && verifyTransferAmount()) {
                startAgentButton.setDisable(false);
            } else {
                startAgentButton.setDisable(true);
            }
        });

        transferField.textProperty().addListener((obs, oldValue, newValue) -> {
            ObservableList<String> styles = transferField.getStyleClass();
            boolean validTrasferField = verifyTransferAmount();
            if (validTrasferField) {
                if (styles.contains("btn-danger")) {
                    styles.remove("btn-danger");
                }
            } else {
                if (!styles.contains("btn-danger")) {
                    styles.add("btn-danger");
                }
            }

            if (verifyTimeInterval() && verifyTransferAmount()) {
                startAgentButton.setDisable(false);
            } else {
                startAgentButton.setDisable(true);
            }
        });

        Platform.runLater(() -> {
            transferField.requestFocus();
        });
    }

    private boolean verifyTransferAmount() {
        String transferAmount = transferField.getText();
        return NUMBER_PATTERN.matcher(transferAmount).find();
    }

    private boolean verifyTimeInterval() {
        String timeInterval = timeIntervalField.getText();
        if (TIME_PATTERN.matcher(timeInterval).find()) {
            Double timeIntervalAmount = Double.parseDouble(timeInterval);
            return (timeIntervalAmount >= 1.0 && !timeInterval.equals("0.0"));
        } else {
            return false;
        }
    }
}
