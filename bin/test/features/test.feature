Feature: Accurate

  Scenario: Test
    Given User login to accurate
    When User click business data
    And User click report menu
    And User click sub menu report list
    And User click memorize menu
    And User click sub menu memorize
    And User set report start and end date
    And User click show sales report button
    Then User see pdf sales report is valid business name
    And User click export button
    And User click export to excel button
    Then User verify success downloaded
