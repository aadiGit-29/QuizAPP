import java.awt.*;
import java.awt.event.*;

public class QuizAppAWT extends Frame implements ActionListener {

    Label questionLabel;
    CheckboxGroup optionsGroup;
    Checkbox option1, option2, option3, option4;
    Button nextButton;
    int currentQuestion = 0;
    int score = 0;

    String[] questions = {
        "How many branches are there in School of computer science?",
        "What is the oldest branch of OUTR?",
        "Who is called the BOND?"
    };

    String[][] options = {
        {"4", "2", "5", "6"},
        {"E&I", "Civil", "Electrical", "CSE"},
        {"Biswajeet Sir", "VC", "BAT-manjeet", "Amit"}
    };

    int[] answers = {0, 0, 0};
    int[] userAnswers = new int[questions.length];

    public QuizAppAWT() {
        setTitle("Quiz Application");
        setSize(700, 500);
        setLayout(new BorderLayout());
        setBackground(new Color(255,192 , 203)); 

       
        Panel questionPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        questionLabel = new Label();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionPanel.add(questionLabel);

        
        optionsGroup = new CheckboxGroup();
        option1 = createStyledCheckbox();
        option2 = createStyledCheckbox();
        option3 = createStyledCheckbox();
        option4 = createStyledCheckbox();

        Panel optionsPanel = new Panel(new GridLayout(4, 1, 5, 5));
        optionsPanel.setBackground(new Color(230, 240, 250));
        optionsPanel.add(option1);
        optionsPanel.add(option2);
        optionsPanel.add(option3);
        optionsPanel.add(option4);

        
        nextButton = new Button("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(this);

        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(nextButton);

        add(questionPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }

        displayQuestion();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private Checkbox createStyledCheckbox() {
        Checkbox cb = new Checkbox("", optionsGroup, false);
        cb.setFont(new Font("Arial", Font.PLAIN, 14));
        cb.setBackground(new Color(99, 200, 255));
        return cb;
    }

    private void displayQuestion() {
        if (currentQuestion < questions.length) {
            questionLabel.setText((currentQuestion + 1) + ". " + questions[currentQuestion]);
            option1.setLabel(options[currentQuestion][0]);
            option2.setLabel(options[currentQuestion][1]);
            option3.setLabel(options[currentQuestion][2]);
            option4.setLabel(options[currentQuestion][3]);
            optionsGroup.setSelectedCheckbox(null);
        } else {
            removeAll();
            setLayout(new GridLayout(questions.length + 3, 1, 5, 5));
            setBackground(new Color(255, 250, 240)); 

            Label scoreLabel = new Label("Your Score: " + score + "/" + questions.length, Label.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
            add(scoreLabel);

            for (int i = 0; i < questions.length; i++) {
                String result = (userAnswers[i] == answers[i]) ? 
                    "✅ Correct" : "❌ Wrong (Correct: " + options[i][answers[i]] + ")";
                Label qLabel = new Label((i + 1) + ". " + questions[i] + " — " + result);
                qLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                add(qLabel);
            }

            Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 10));
            Button restartButton = new Button("Restart");
            Button exitButton = new Button("Exit");



            restartButton.setBackground(new Color(0, 150, 0));
            restartButton.setForeground(Color.WHITE);
            exitButton.setBackground(new Color(200, 0, 0));
            exitButton.setForeground(Color.WHITE);

            restartButton.addActionListener(ae -> {
                score = 0;
                currentQuestion = 0;
                for (int i = 0; i < userAnswers.length; i++) {
                    userAnswers[i] = -1;
                }
                removeAll();
                setLayout(new BorderLayout());
                setBackground(new Color(240, 248, 255));
                add(new Panel(new FlowLayout(FlowLayout.CENTER)) {{
                    add(questionLabel);
                }}, BorderLayout.NORTH);
                Panel optionsPanel = new Panel(new GridLayout(4, 1, 5, 5));
                optionsPanel.setBackground(new Color(230, 240, 250));
                optionsPanel.add(option1);
                optionsPanel.add(option2);
                optionsPanel.add(option3);
                optionsPanel.add(option4);
                add(optionsPanel, BorderLayout.CENTER);
                Panel btnPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
                btnPanel.add(nextButton);
                add(btnPanel, BorderLayout.SOUTH);
                displayQuestion();
                validate();
            });

            exitButton.addActionListener(ae -> System.exit(0));

            buttonPanel.add(restartButton);
            buttonPanel.add(exitButton);
            add(buttonPanel);
            validate();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Checkbox selected = optionsGroup.getSelectedCheckbox();
        if (selected != null) {
            String selectedLabel = selected.getLabel();
            int selectedIndex = -1;

            for (int i = 0; i < 4; i++) {
                if (options[currentQuestion][i].equals(selectedLabel)) {
                    selectedIndex = i;
                    break;
                }
            }
            userAnswers[currentQuestion] = selectedIndex;
            if (selectedIndex == answers[currentQuestion]) {
                score++;
            }
        } else {
            userAnswers[currentQuestion] = -1;
        }
        currentQuestion++;
        displayQuestion();
    }

    public static void main(String[] args) {
        new QuizAppAWT();
    }
}
