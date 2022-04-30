Browserstack Test Framework Example for ABC Inc.
================================================

Prepared by Aaron Evans, Solution Architect

Based on our discussions, I've put together an example test framework that demonstrates the principles we've talked about, and incorporating the decisions made during our discussions.

Test Framework Features
-----------------------

### Base Test
### Driver Factory
### Page Object Abstraction
### Synchronization
### Configuration
### Reporting
### Parallelization
### Cross-platform execution
### CI/CD pipeline integration

Read more about the Framework at [Framework.md]

#### Test Tagging

Tests may be added to one or more group using the @Tag annotation at the top of the test case.
By default, all tests will be run unless marked with an excluded tag.

Common tags
 * @smoke
 * @regression
 * @flow
 * @bug [bugid]

Feature tags:

 * @login
 * @vendors
 * @order
 * @offers
 * @orders
 * @cart


Decisions
---------

### Programming Language

Because full stack development is done using Java/Springboot, and to leverage developer expertise, the E2E Test framework will also be written in Java.  

This will make it more difficult for some people to contribute, including
 * UI developers -- who would be more comfortable using JavaScript
 * Non-technical users -- who would prefer a no-code way to contribute tests

However, most test automation is consumed and executed by technical users, and reporting will allow non-developers to digest test results (if not add directly to test cases).


### Environment

Automated tests will run primarily on the dedicated staging environment, but can be exercised by developers locally and in fact are encouraged to be run in development environments.  However, functionality coverage in tests should follow feature branching naming conventions so that in-development features (and changed UI locators) should exist in same-named feature branches and the "main" branch should reflect the stage environment.

Some tests may be marked to run in production for smoke testing and monitoring, but none have been identified yet.  It is important to *not* run a full test regression suite in production because of security and test data concerns.

### Test Ownership

QA will own the test automation framework, but expect contributions from development. 
Automated test cases, however, that utilize the framework will be the responsibility of specific feature development teams (where possible to isolate.)
If a test is broken, or uncovers a deficiency, it is the responsibility of the development team to fix it.  Team members focusing on QA will typically be the ones to identify issues with tests, and most often fix them.

### Requirements Coverage

We have chosen to map automated tests to features instead of mapping to specific requirements, in the interest of ease of categorizing, and to avoid too-detailed specification (leading to analysis paralysis) or too complex of reporting.  Many tests are cross-cutting and will cover multiple features, so a moderate level of granularity is preferred.

### Platform Coverage

Tests are designed to run cross-platform and target all supported browsers:
 * Chrome latest, latest-1
 * Safari latest
 * Edge latest, latest-1

Mobile testing is currently out of scope, but planned for future support.

### Test Execution Goals

Only a few sample tests have been provided, and feature teams should determine the number of tests they need to provide adequate coverage.  However, we recommend test suites (per product) number in the hundreds *not thousands* for maintainability reasons.

At this time, we recommend targeting only a few tens of tests covering basic core functionality, and expand from there with lessons learned.

This framework should be extensible, and able to grow with testing needs in both quantity and scale, but we recommend starting small and developing common patterns before expanding to target exhaustive coverage.

Data driven tests that cover multiple scenarios while using the same test code should be considered where ever possible as opposed to duplicate tests for each data point. See the vendor test for an example data-driven test.

#### Number of Tests

Current goal is to expand beyond the basic few example provided tests to have up to 100 test cases for the main workflow.  More tests by product team may be added later.

#### Execution Speed

Tests should target execution in 5 minutes or less.  Tests running for more than 15 minutes should be killed by CI, and isolated.  If needed, long-running tests can be executed manually.  Any test that repeatedly takes longer than 5 minutes to run should be marked with the @slow tag.

#### Parallelization

10 Tests will run in parallel during our proof of concept period.  Test concurrency can be increased at additional availability is purchased from Browserstack

### Libraries

The test framework uses the current Selenium-Java library (4.1.2) and AssertJ assertions based on existing unit test patterns.  Maven Surefire plugin is used for running tests in parallel and tagging 

Reporting library ....

### Continuous Delivery

Tests are designed to be able to run locally (against either a local browser or against the browserstack cloud) but also to run in CI/CD using Github Actions.  In CI, tests will make use of browserstack exclusively instead of using a local headless chromedriver (which can be difficult to setup and unreliable) or relying on a self-hosted grid.

A test run will be triggered in CI every time the test framwork changes, or when an execution is triggered manually or via the Github API  using "repository dispatch" event by the following events in staging:

 * deployment
 * configuration change
 * data refresh

A daily scheduled regression test run will also be executed.

### Version Control
The E2E test framework will live in its own repository, separate from production code, because: 
1. It will be maintained by QA 
2. It may be used outside individual development team.
3. It is cross-cutting across multiple development repos

One downside to this decision is that it will not be able to use version control to track with the UI source code, but since testing will likely lag behind development for some time, this is ok. 