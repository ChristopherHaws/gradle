/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.tasks.testing.retrying;

import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.testing.Test;

public class JvmRetrySpecProvider {

    private final Provider<Long> retryUntilFailureCount;
    private final Provider<Long> retryUntilStoppedCount;

    private JvmRetrySpecProvider(Provider<Long> retryUntilFailureCount, Provider<Long> retryUntilStoppedCount) {
        this.retryUntilFailureCount = retryUntilFailureCount;
        this.retryUntilStoppedCount = retryUntilStoppedCount;
    }

    public JvmRetrySpec get() {
        return JvmRetrySpec.of(retryUntilFailureCount, retryUntilStoppedCount);
    }

    public static JvmRetrySpecProvider of(Test task) {
        return new JvmRetrySpecProvider(task.getRetryUntilFailureCount(), task.getRetryUntilStoppedCount());
    }
}
