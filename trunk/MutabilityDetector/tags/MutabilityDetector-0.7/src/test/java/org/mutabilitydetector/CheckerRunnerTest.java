/*
 *    Copyright (c) 2008-2011 Graham Allan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.mutabilitydetector;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mutabilitydetector.locations.Dotted.fromClass;

import org.junit.Test;
import org.mockito.Mockito;
import org.mutabilitydetector.checkers.IMutabilityChecker;

public class CheckerRunnerTest {

    @Test
    public void willVisitAnalysisExceptionWhenAnUnhandledExceptionIsThrown() {
        IMutabilityChecker checker = Mockito.mock(IMutabilityChecker.class);

        Throwable toBeThrown = new NoSuchMethodError();
        doThrow(toBeThrown).when(checker).visit(anyInt(),
                anyInt(),
                anyString(),
                anyString(),
                anyString(),
                new String[] { anyString() });

        CheckerRunner checkerRunner = CheckerRunner.createWithCurrentClasspath();

        checkerRunner.run(new AnalysisSession(), checker, fromClass(CheckerRunner.class));

        verify(checker).visitAnalysisException(toBeThrown);
    }

}