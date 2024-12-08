package org.poo.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.actions.*;
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

        final List<User> users = UserMapper.mapToUsers(inputData.getUsers());

        final ArrayNode output = objectMapper.createArrayNode();

        final List<ExchangeRate> exchangeRates = ExchangeRateMapper.mapToExchangeRates(inputData.getExchangeRates());
        final CurrencyConverter currencyConverter = new CurrencyConverter(exchangeRates);

        for (final CommandInput command : inputData.getCommands()) {
            CommandHandler handler = null;

            switch(command.getCommand()) {
                case "printUsers" :
                    handler = new PrintUsers(users, command.getTimestamp());
                    break;
                case "addAccount" :
                   final AddAccount addAccount = new AddAccount(
                           command.getEmail(),
                           command.getCurrency(),
                           command.getAccountType(),
                           command.getInterestRate(),
                           command.getTimestamp(),
                           users);
                    addAccount.execute(output);
                    break;
                case "addFunds" :
                    handler = new AddFunds(
                            command.getAccount(),
                            command.getAmount(),
                            command.getTimestamp(),
                            users);
                    break;
                case "createCard" :
                    handler = new AddCards(
                            command.getAccount(),
                            command.getEmail(),
                            command.getCardNumber(),
                            false,
                            command.getTimestamp(),
                            users);
                    break;
                case "createOneTimeCard" :
                    handler = new AddCards(
                            command.getAccount(),
                            command.getEmail(),
                            command.getCardNumber(),
                            true,
                            command.getTimestamp(),
                            users);
                    break;
                case "deleteAccount" :
                    handler = new DeleteAccount(
                            command.getAccount(),
                            command.getTimestamp(),
                            command.getEmail(),
                            users);
                    break;
                case "deleteCard" :
                    handler = new org.poo.actions.DeleteCard(
                            command.getCardNumber(),
                            command.getTimestamp(),
                            users);
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
                    final CommandInvoker invoker = new CommandInvoker();
                    invoker.addCommand(payOnline);

                    invoker.executeCommands(output);
                    break;
            }
            if (handler != null) {
                handler.execute(output);
            }
        }

        /*
         * TODO Implement your function here
         *
         * How to add output to the output array?
         * There are multiple ways to do this, here is one example:
         *
         * ObjectMapper mapper = new ObjectMapper();
         *
         * ObjectNode objectNode = mapper.createObjectNode();
         * objectNode.put("field_name", "field_value");
         *
         * ArrayNode arrayNode = mapper.createArrayNode();
         * arrayNode.add(objectNode);
         *
         * output.add(arrayNode);
         * output.add(objectNode);
         *
         */

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
