package controller;

import javafx.stage.Stage;
import model.Account;
import model.AccountModel;
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
    }

    @Test
    public void testAccounts_initAccountsGetSortedByID() {
    }
}
