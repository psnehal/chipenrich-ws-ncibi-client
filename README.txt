To configure the client copy client.properties.template to client.properties (located in src/main/resources). 
Then modify the single property in it so that it looks as follows:
default.server.url=http://mimitest.ncibi.org/ncibiws/

The client depends on the following projects (run: 'mvn -Dmaven.test.skip=true install' on each):

ncibi-commons:  https://www.umms.med.umich.edu/codestore/ncibi/commons/trunk
ws-common: https://www.umms.med.umich.edu/codestore/ncibi/WebBackend/WebServices/ws-common/trunk
ncibi-db: https://www.umms.med.umich.edu/codestore/ncibi/WebBackend/ncibi-db/trunk
ws-client:  https://www.umms.med.umich.edu/codestore/ncibi/WebBackend/WebServices/ws-client/trunk
lrpath-lib: https://www.umms.med.umich.edu/codestore/ncibi/projects/WebServices/lrpath-lib/trunk

The ws-ncibi-client project has a set of unit tests in it that shows you how to use the API (under src/test/java).
