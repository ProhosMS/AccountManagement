package controller;

import javafx.stage.Stage;
import model.Account;
import org.mockito.Mockito;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public class IndexControllerTest {

    private IndexController controller;
    private List<Account> accounts;
    private Stage stage = Mockito.mock(Stage.class);

    @BeforeMethod
    public void setUp() {
        controller = new IndexController();
        accounts = Arrays.asList(
                new Account("Person1", "100", 200.0),
                new Account("Person2", "200", 300.0),
                new Account("Person3", "400", 400.0),
                new Account("Person4", "300", 400.0)
        );
    }

    @Test
    public void testModels_getUpdated() {
        /* Since accounts are sorted by ids, binary search would be a good idea */

        /* initialze controller with specific accounts */
        controller.init(stage, accounts);

        /* initialze updated account */
        Account updatedAccount = new Account("Person1", "100", 400.0);

        /* update models */
        controller.updateAccount(updatedAccount);

        /* get list of accounts from controller */
        List<Account> updatedAccounts = controller.getAccounts();

        /* define expected accounts when they are udpated */
        List<Account> expectedAccounts = Arrays.asList(
                new Account("Person1", "100", 400.0),
                new Account("Person2", "200", 300.0),
                new Account("Person4", "300", 400.0),
                new Account("Person3", "400", 400.0)
        );

        assertEquals(updatedAccounts, expectedAccounts);
    }

    @Test
    public void testAccounts_initAccountsGetSortedByID() {
        /* accounts are unsorted by default */
        controller.init(stage, accounts);

        List<Account> accounts = controller.getAccounts();

        List<Account> expectedAccounts = Arrays.asList(
                new Account("Person1", "100", 400.0),
                new Account("Person2", "200", 300.0),
                new Account("Person4", "300", 400.0),
                new Account("Person3", "400", 400.0)
        );

        assertEquals(accounts, expectedAccounts);
    }
}
