// ***********************************************************
// This example support/e2e.ts is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

// Import commands.js using ES2015 syntax:
import './commands.ts'

declare global {
    // eslint-disable-next-line @typescript-eslint/no-namespace
    namespace Cypress {
        interface Chainable {
            /**
             * Retrieves the element that has `selectorId` as its ID.
             *
             * @param selectorId The ID of the element to retrieve.
             */
            getBySelectorId(selectorId: string): Chainable<Element>

            /**
             * Navigates to root page and clicks on the project name that should appear
             * in the sidebar to navigate to the project page
             *
             * @param projectName Name of the project
             */
            navigateToProjectPage(projectName: string): Chainable<Element>
        }
    }
}

// Alternatively you can use CommonJS syntax:
// require('./commands')