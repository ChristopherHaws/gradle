/*
 * Copyright 2024 the original author or authors.
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

package org.gradle.problems.internal.rendering;

import org.gradle.api.problems.ProblemId;
import org.gradle.api.problems.internal.GeneralData;
import org.gradle.api.problems.internal.Problem;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemRenderer {

    private final PrintWriter output;

    public ProblemRenderer(Writer writer) {
        output = new PrintWriter(writer);
    }

    public void render(List<Problem> problems) {
        Map<ProblemId, List<Problem>> renderingGroups = new HashMap<>();
        for (Problem problem : problems) {
            List<Problem> groupedProblems = renderingGroups.computeIfAbsent(
                problem.getDefinition().getId(),
                id -> new ArrayList<>()
            );
            groupedProblems.add(problem);
        }

        renderingGroups.forEach((id, groupedProblems) -> renderSingleProblemGroup(id, groupedProblems));
    }

    public void render(Problem problem) {
        this.render(Collections.singletonList(problem));
    }

    private void renderSingleProblemGroup(ProblemId id, List<Problem> groupedProblems) {
        output.printf(
            "%s (%s)%n", id.getDisplayName(), id
        );
        groupedProblems.forEach(this::renderSingleProblem);
    }

    private void renderSingleProblem(Problem problem) {
        String formattedMessage = ((GeneralData) problem.getAdditionalData()).getAsMap().get("formatted");
        for (String line : formattedMessage.split("\n")) {
            output.printf("  %s%n", line);
        }
    }

}
