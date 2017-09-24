# jOmnipod

jOmnipod is a Java library for parsing the __IBF__ files created by the __Omnipod PDM__. jOmnipod requires __Java 8__ and is available under the __MIT license__.

## Your first program

	public class Main {

		public static void main(String[] args) throws IOException {
			InputStream inputStream = new FileInputStream(new File("my.ibf"));

			IBF ibf = new IBF(inputStream);

			for (LogRecord logRecord : ibf.logRecords()) {
				System.out.print(logRecord.timestamp() + " : ");
				logRecord.accept(new LogRecordVisitor() {

					@Override
					public void visit(IgnoreLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(DeletedLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(PumpAlarmDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(UnknownLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(OcclusionLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(DownloadLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(ResumeLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(TerminateBasalLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(TerminateBolusLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(CarbLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(BloodGlucoseLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(AlarmLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(RemoteHazardAlarmLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(SuggestedCalcLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(DateChangeLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(SuspendLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(BasalLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(BolusLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(TimeChangeLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(DeactivateLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(EndMarkerLogRecordDetails details) {
						System.out.println(details.toString());
					}

					@Override
					public void visit(ActivateLogRecordDetails details) {
						System.out.println(details.toString());
					}
				});
			}
		}
	}
