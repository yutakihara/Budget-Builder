import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class AnalysisFrame extends JFrame {

    private BudgetAnalysis budgetAnalysis;
    //EXCLUDED FOR TESTING
    //private BudgetProfile profile = CategoryFrame.budgetProfile;

    //TESTING ONLY
    private BudgetProfile profile;
    private JPanel listPanel;
    private JPanel incomePanel;
    private JPanel spendingPanel;
    private JPanel savingsPanel;
    private JPanel incomeListPanel;
    private JPanel spendingListPanel;
    private String type;


    //Must be called with arg being "IncomeNeeded", "UtilityBudget", or "LeisureMoney"
    public AnalysisFrame(String type) {

        //TESTING ONLY
        profile = new BudgetProfile("Chris", "Gilmore");
        BudgetEditor editor = new BudgetEditor(profile);
        Spending spending = new Spending("Car", 500);
        Spending spending2 = new Spending("House", 2000);
        Income income = new Income("Work", 3000);
        Income income2 = new Income("Stocks", 5000);
        editor.addSpending(spending);
        editor.addSpending(spending2);
        editor.addIncome(income);
        editor.addIncome(income2);
        profile.setSavings(50);

        budgetAnalysis = new BudgetAnalysis(profile);

        this.type = type;
        this.setTitle("Budget Analysis Results");
        this.setPreferredSize(new Dimension(1000, 800));
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUpFrame();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setUpFrame() {
        setListContainer();
        JLabel mainTitle = new JLabel("Budget Analysis");
        mainTitle.setFont(new Font("Century", Font.ITALIC, 60));
        mainTitle.setHorizontalAlignment(JLabel.CENTER);
        this.add(mainTitle, BorderLayout.NORTH);
        this.add(listPanel, BorderLayout.CENTER);
    }

    public void setListContainer() {
        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 3));

        setIncomePanel();
        setSpendingPanel();
        setSavingsPanel();

        listPanel.add(incomePanel);
        listPanel.add(spendingPanel);
        listPanel.add(savingsPanel);
    }

    public void setIncomePanel() {
        incomePanel = new JPanel();
        JLabel incomeTitle = null;
        incomePanel.setLayout(new GridLayout(2, 1));
        setIncomeListPanel();

        if (type == "IncomeNeeded") {
            incomeTitle = new JLabel("Income Needed");
        }
        if (type == "UtilityBudget" || type == "LeisureMoney") {
            incomeTitle = new JLabel("Income");
        }

        incomeTitle.setFont(new Font("Century", Font.BOLD, 30));
        incomeTitle.setHorizontalAlignment(JLabel.CENTER);


        incomePanel.add(incomeTitle);
        incomePanel.add(incomeListPanel);

    }

    public void setSpendingPanel() {
        spendingPanel = new JPanel();
        spendingPanel.setLayout(new GridLayout(2, 1));
        JLabel spendingTitle = null;
        setSpendingListPanel();

        if (type == "IncomeNeeded" || type == "LeisureMoney") {
            spendingTitle = new JLabel("Spendings");
        }
        if (type == "UtilityBudget") {
            spendingTitle = new JLabel("Utility Budget");
        }

        spendingTitle.setFont(new Font("Century", Font.BOLD, 30));
        spendingTitle.setHorizontalAlignment(JLabel.CENTER);

        spendingPanel.add(spendingTitle);
        spendingPanel.add(spendingListPanel);
    }

    public void setSavingsPanel() {
        savingsPanel = new JPanel();
        JLabel savingsTitle = null;
        JLabel savingsAmount = null;
        savingsPanel.setLayout(new GridLayout(2, 1));

        if (type == "IncomeNeeded" || type == "UtilityBudget") {
            savingsTitle = new JLabel("Savings");
            savingsAmount = new JLabel("$" + ((Double) profile.getSavings()).toString());
        }
        if (type == "LeisureMoney") {
            savingsTitle = new JLabel("Leisure Money");
            savingsAmount = new JLabel("$" + budgetAnalysis.calculateSavings().toString());
        }

        savingsTitle.setFont(new Font("Century", Font.BOLD, 30));
        savingsTitle.setHorizontalAlignment(JLabel.CENTER);

        savingsAmount.setFont(new Font("Century", Font.BOLD, 30));
        savingsAmount.setVerticalAlignment(JLabel.NORTH);
        savingsAmount.setHorizontalAlignment(JLabel.CENTER);


        savingsPanel.add(savingsTitle);
        savingsPanel.add(savingsAmount);

    }


    public void setIncomeListPanel() {
        incomeListPanel = new JPanel();
        incomeListPanel.setLayout(new GridLayout(10, 1));

        if (type == "IncomeNeeded") {
            //Displays analyzed income needed based on user spending and savings input
            String amount = "$" + budgetAnalysis.calculateIncome();
            JLabel calculatedIncome = new JLabel(amount);
            calculatedIncome.setFont(new Font("Century", Font.BOLD, 30));
            calculatedIncome.setHorizontalAlignment(JLabel.CENTER);

            incomeListPanel.add(calculatedIncome);
        } else {

            if (profile.getIncomeList().size() > 0) {
                ArrayList<Income> incomeList = profile.getIncomeList();
                for (int i = 0; i < incomeList.size(); i++) {
                    JPanel listComponent = createComponent(incomeList.get(i).getCategory(), incomeList.get(i).getAmount());
                    incomeListPanel.add(listComponent);
                }
            }
        }
    }


    public void setSpendingListPanel() {
        spendingListPanel = new JPanel();
        spendingListPanel.setLayout(new GridLayout(10, 1));

        //Displays analyzed utility budget based on user income and savings input
        if (type == "UtilityBudget") {
            String amount = "$" + budgetAnalysis.calculateSpending();
            JLabel calculatedSpending = new JLabel(amount);
            calculatedSpending.setFont(new Font("Century", Font.BOLD, 30));
            calculatedSpending.setHorizontalAlignment(JLabel.CENTER);

            spendingListPanel.add(calculatedSpending);
        } else {
            if (profile.getSpendingList().size() > 0) {
                ArrayList<Spending> spendingList = profile.getSpendingList();
                for (int i = 0; i < spendingList.size(); i++) {
                    JPanel listComponent = createComponent(spendingList.get(i).getCategory(), spendingList.get(i).getAmount());
                    spendingListPanel.add(listComponent);
                }
            }
        }
    }


    public JPanel createComponent(String category, double amount) {
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(1, 2));
        JLabel listName = new JLabel(category);
        JLabel listAmount = new JLabel((("$" + amount)));
        listPanel.add(listName);
        listPanel.add(listAmount);
        BevelBorder border = new BevelBorder(BevelBorder.RAISED);
        listPanel.setBorder(border);
        return listPanel;
    }

}


