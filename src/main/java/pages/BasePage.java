package pages;

import com.codeborne.selenide.SelenideElement;
import step.TableStep;
import wrappers.DropDown;

import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {

    protected final SelenideElement logo = $(".d-inline-block.align-top");
    protected final SelenideElement logoLink = $(".navbar-brand");
    protected final TableStep tableStep = new TableStep();
    //-----Users
    public final DropDown usersReadAllTab = new DropDown("Users", "Read all");
    public final DropDown usersReadUsersWithCarsTab = new DropDown("Users", "Read user with cars");
    public final DropDown usersCreateNewTab = new DropDown("Users", "Create new");
    public final DropDown usersAddMoneyTab = new DropDown("Users", "Add money");
    public final DropDown usersBuyOrSellCarTab = new DropDown("Users", "Buy or sell car");
    public final DropDown usersSettleToHouseTab = new DropDown("Users", "Settle to house");
    public final DropDown usersIssueALoanTab = new DropDown("Users", "Issue a loan");
    //-----Cars
    public final DropDown carsReadAllTab = new DropDown("Cars", "Read all");
    public final DropDown carsCreateNewTab = new DropDown("Cars", "Create new");
    public final DropDown carsBuyOrSellCarTab = new DropDown("Cars", "Buy or sell car");
    //-----Houses
    public final DropDown housesReadAllTab = new DropDown("Houses", "Read all");
    public final DropDown housesIssueALoanTab = new DropDown("Houses", "Read one by ID");
    public final DropDown housesCreateNewTab = new DropDown("Houses", "Create new");
    public final DropDown housesSettleOrEvictUserTab = new DropDown("Houses", "Settle or evict user");
}