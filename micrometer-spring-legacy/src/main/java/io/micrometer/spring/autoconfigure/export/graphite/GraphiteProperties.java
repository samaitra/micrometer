/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micrometer.spring.autoconfigure.export.graphite;

import io.micrometer.graphite.GraphiteProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * {@link ConfigurationProperties} for configuring Graphite metrics export.
 *
 * @author Jon Schneider
 */
@ConfigurationProperties(prefix = "spring.metrics.export.graphite")
public class GraphiteProperties {
    /**
     * Enable publishing to the backend.
     */
    private Boolean enabled = true;

    /**
     * The step size (reporting frequency) to use.
     */
    private Duration step = Duration.ofMinutes(1);

    /**
     * The base time unit used to report rates.
     */
    private TimeUnit rateUnits;

    /**
     * The base time unit used to report durations.
     */
    private TimeUnit durationUnits;

    /**
     * Graphite host used for publishing.
     */
    private String host;

    /**
     * Graphite port used for publishing.
     */
    private Integer port;

    /**
     * Protocol to use while shipping data to Graphite.
     */
    private GraphiteProtocol protocol = GraphiteProtocol.Pickled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getStep() {
        return step;
    }

    public void setStep(Duration step) {
        this.step = step;
    }

    public TimeUnit getRateUnits() {
        return rateUnits;
    }

    public void setRateUnits(TimeUnit rateUnits) {
        this.rateUnits = rateUnits;
    }

    public TimeUnit getDurationUnits() {
        return durationUnits;
    }

    public void setDurationUnits(TimeUnit durationUnits) {
        this.durationUnits = durationUnits;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public GraphiteProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(GraphiteProtocol protocol) {
        this.protocol = protocol;
    }
}
