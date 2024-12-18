package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.actions.*;
import org.poo.bankInput.Account;
//import org.poo.bankInput.Commerciants;
import org.poo.bankInput.ExchangeRate;
import org.poo.handlers.*;
import org.poo.bankInput.User;
import org.poo.checker.Checker;
import org.poo.checker.CheckerConstants;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        final File directory = new File(CheckerConstants.TESTS_PATH);
        final Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            final File resultFile = new File(String.valueOf(path));
            for (final File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        final var sortedFiles = Arrays.stream(Objects.requireNonNull(directory.listFiles())).
                sorted(Comparator.comparingInt(Main::fileConsumer))
                .toList();

        for (final File file : sortedFiles) {
            final String filepath = CheckerConstants.OUT_PATH + file.getName();
            final File out = new File(filepath);
            final boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        Utils.resetRandom();
        final ObjectMapper objectMapper = new ObjectMapper();
        final File file = new File(CheckerConstants.TESTS_PATH + filePath1);
        final ObjectInput inputData = objectMapper.readValue(file, ObjectInput.class);

        final CommandInvoker invoker = new CommandInvoker();

        final List<User> users = UserMapper.mapToUsers(inputData.getUsers());

        final ArrayNode output = objectMapper.createArrayNode();

        final List<ExchangeRate> exchangeRates = ExchangeRateMapper.mapToExchangeRates(inputData.getExchangeRates());
        final CurrencyConverter currencyConverter = new CurrencyConverter(exchangeRates);

        final List<Account> accounts = AccountExtractor.extractAccountsFromUsers(users);

        //final List<Commerciants> commerciantsList = Commerciants.fromInputList(CommerciantInput.getCommerciants());


        for (final CommandInput command : inputData.getCommands()) {

            switch(command.getCommand()) {
                case "printTransactions":
                    final PrintTransactionsCommand printTransactionsCommand = new PrintTransactionsCommand(
                            command.getEmail(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(printTransactionsCommand);
                    invoker.executeCommands(output);
                    break;
                case "printUsers" :
                    invoker.addCommand(new PrintUsersCommand(users, command.getTimestamp()));
                    invoker.executeCommands(output);
                    break;
                case "addAccount" :
                   final AddAccountCommand addAccountCommand = new AddAccountCommand(
                           command.getEmail(),
                           command.getCurrency(),
                           command.getAccountType(),
                           command.getInterestRate(),
                           command.getTimestamp(),
                           users);
                    invoker.addCommand(addAccountCommand);
                    invoker.executeCommands(output);
                    break;
                case "addFunds" :
                    final AddFundsCommand addFundsCommand =  new AddFundsCommand(
                            command.getAccount(),
                            command.getAmount(),
                            command.getTimestamp(),
                            users);
                    invoker.addCommand(addFundsCommand);
                    invoker.executeCommands(output);
                    break;
                case "createCard" :
                    final AddCardsCommand addCardsCommand =  new AddCardsCommand(
                            command.getAccount(),
                            command.getEmail(),
                            false,
                            command.getTimestamp(),
                            users);
                    invoker.addCommand(addCardsCommand);
                    invoker.executeCommands(output);
                    break;
                case "createOneTimeCard" :
                    final AddCardsCommand addOneTimeCards = new AddCardsCommand(
                            command.getAccount(),
                            command.getEmail(),
                            true,
                            command.getTimestamp(),
                            users);
                    invoker.addCommand(addOneTimeCards);
                    invoker.executeCommands(output);
                    break;
                case "deleteAccount" :
                    final DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(
                            command.getAccount(),
                            command.getTimestamp(),
                            command.getEmail(),
                            users);
                    invoker.addCommand(deleteAccountCommand);
                    invoker.executeCommands(output);
                    break;
                case "deleteCard" :
                   final DeleteCardCommand deleteCardCommand = new DeleteCardCommand(
                            command.getCardNumber(),
                            command.getTimestamp(),
                            users);
                   invoker.addCommand(deleteCardCommand);
                   invoker.executeCommands(output);
                    break;
                case "payOnline" :
                    final PayOnlineCommand payOnline = new PayOnlineCommand(
                            command.getCardNumber(),
                            command.getAmount(),
                            command.getCurrency(),
                            command.getTimestamp(),
                            command.getDescription(),
                            command.getCommerciant(),
                            command.getEmail(),
                            users,
                            currencyConverter
                    );
                    invoker.addCommand(payOnline);
                    invoker.executeCommands(output);
                    break;
                case "sendMoney" :
                    final SendMoneyCommand sendMoney = new SendMoneyCommand(
                            command.getAccount(),
                            command.getAmount(),
                            command.getReceiver(),
                            command.getTimestamp(),
                            command.getDescription(),
                            accounts,
                            currencyConverter,
                            users
                    );

                    invoker.addCommand(sendMoney);
                    invoker.executeCommands(output);
                    break;
                case "setAlias" :
                    final SetAliasCommand setAlias = new SetAliasCommand(
                            command.getEmail(),
                            command.getAlias(),
                            command.getAccount(),
                            users);
                    invoker.addCommand(setAlias);
                    invoker.executeCommands(output);
                    break;
                case "setMinBalance" :
                    final SetMinBalanceCommand setMinBalance = new SetMinBalanceCommand(
                            command.getAmount(),
                            command.getAccount(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(setMinBalance);
                    invoker.executeCommands(output);
                    break;
                case "checkCardStatus" :
                    final CheckCardStatusCommand checkCardStatus = new CheckCardStatusCommand(
                            command.getCardNumber(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(checkCardStatus);
                    invoker.executeCommands(output);
                    break;
                case "splitPayment" :
                    final SplitPaymentCommand splitPayment = new SplitPaymentCommand(
                            command.getAccounts(),
                            command.getTimestamp(),
                            command.getCurrency(),
                            command.getAmount(),
                            users,
                            currencyConverter
                    );
                    invoker.addCommand(splitPayment);
                    invoker.executeCommands(output);
                    break;
                case "report" :
                    final ReportPrintCommand reportPrintCommand = new ReportPrintCommand(
                            command.getStartTimestamp(),
                            command.getEndTimestamp(),
                            command.getAccount(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(reportPrintCommand);
                    invoker.executeCommands(output);
                    break;
                case "spendingsReport" :
                    final SpendingReportPrintCommand spendingReportPrintCommand = new SpendingReportPrintCommand(
                            command.getStartTimestamp(),
                            command.getEndTimestamp(),
                            command.getAccount(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(spendingReportPrintCommand);
                    invoker.executeCommands(output);
                    break;
                case "addInterest" :
                    final AddInterestCommand addInterestCommand = new AddInterestCommand(
                            command.getTimestamp(),
                            command.getAccount(),
                            command.getInterestRate(),
                            users
                    );
                    invoker.addCommand(addInterestCommand);
                    invoker.executeCommands(output);
                    break;
                case "changeInterestRate" :
                    final ChangeInterestRateCommand changeInterestRateCommand = new ChangeInterestRateCommand(
                            command.getAccount(),
                            command.getInterestRate(),
                            command.getTimestamp(),
                            users
                    );
                    invoker.addCommand(changeInterestRateCommand);
                    invoker.executeCommands(output);
                    break;
            }
        }

        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    /**
     * Method used for extracting the test number from the file name.
     *
     * @param file the input file
     * @return the extracted numbers
     */
    public static int fileConsumer(final File file) {
        final String fileName = file.getName()
                .replaceAll(CheckerConstants.DIGIT_REGEX, CheckerConstants.EMPTY_STR);
        return Integer.parseInt(fileName.substring(0, 2));
    }
}
