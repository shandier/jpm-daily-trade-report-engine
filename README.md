# jpm-daily-trade-report-engine
A simple daily trade reporting engine for incoming instructions. It's inputs are:
1. Source to read instruction(`CSV file path at this point`)
2. Trade type for the report (**case insenstive**) `b for buy` and `s for sell`
3. date in `dd-MMM-yyyy format`, for which report needs to be generated. 


# Engine Modules:
This reporting engine has 4 modules
1. **DataReader**: This is a simple `Stratergy pattern implementtion` to read Instructions from various resources. For the scope of Engine I have implemented and read instructions from File only.
2. **InstructionManager**: Purpose of this service is to `process instructions` and apply `trade and instruction settlement rules` on the istructions, provided by the client.
3. **ReportManager**: Report manager is responsible for `creating daily trade report and dashboard` for given trade type and date and print report on the console.
4. **Main**: This is just an end point to execute the report engine. This has a main method which takes the required inputs from user as console args.

**Note**: Sample CSV is uploaded in resources folder.

# How to execute Jar:
1. Downlod the jar and sample CSV file from resources folder.
2. Open the cmd or shell and go to path where downloaded jar is places.
3. Use command **java -jar <jarname> [parameterlist]** to execute jar.
4. **java -jar dailyTradeReportEngine.jar tradeInstructions.csv b 04-Jan-2016**.
5. Place CSV file at the same locstion of jar otherwise provide full path of file.
6. Parameter list as in order:

        a. File path
        b. Trade type
        c. Date
        
# How to run on Eclipse :
1. Checkout as simple java project.
2. Go to Main class
3. Right click and select run as --> run configuration
4. Select arguments tab and add arguments and run.

