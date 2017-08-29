package nebula.plugin.info.ci

import groovy.transform.CompileStatic
import org.gradle.api.Project

@CompileStatic
class GoCdProvider extends AbstractContinuousIntegrationProvider {
    static final String JOB_NAME = 'GO_JOB_NAME'
    static final String PIPELINE_COUNTER = 'GO_PIPELINE_COUNTER'
    static final String PIPELINE_NAME = 'GO_PIPELINE_NAME'
    static final String SERVER = 'GO_SERVER_URL'
    static final String STAGE_COUNTER = 'GO_STAGE_COUNTER'
    static final String STAGE_NAME = 'GO_STAGE_NAME'

    @Override
    boolean supports(Project project) {
        getEnvironmentVariable(SERVER)
    }

    @Override
    String calculateHost(Project project) {
        getEnvironmentVariable(SERVER)
    }

    @Override
    String calculateJob(Project project) {
        concatEnvironmentVariables([PIPELINE_NAME, STAGE_NAME, JOB_NAME], '/')
    }

    @Override
    String calculateBuildNumber(Project project) {
        getEnvironmentVariable(PIPELINE_COUNTER)
    }

    @Override
    String calculateBuildId(Project project) {
        concatEnvironmentVariables([PIPELINE_COUNTER, STAGE_COUNTER], '.')
    }

    private static String concatEnvironmentVariables(Iterable<String> components, String separator) {
        components.inject { String s -> getEnvironmentVariable(s) }.join(separator)
    }
}
