import {generateRandomNumber} from '../support/utils';

function buildProjectName(id: string): string {
    return 'project ' + id;
}

function buildProjectSmallName(id: string): string {
    return id;
}

const projectSeed = generateRandomNumber();
const projectName = buildProjectName(projectSeed);
const projectSmallName = buildProjectSmallName(projectSeed);

const newProjectSeed = generateRandomNumber();
const projectNewName = buildProjectName(newProjectSeed)
const projectNewSmallName = buildProjectSmallName(newProjectSeed);

/**
 * Fills project form with given arguments and then clicks the submit button.
 * Can be used either to create a new project or edit an already existing one

 * Pre-requisite: Be on the project creation/edition page
 *
 * @param name Name to give to your project
 * @param smallName Small name to give to your project
 */
function fillProjectFormAndSubmit(name: string, smallName: string) {
    cy.getBySelectorId('name')
        .clear()
        .type(name)
        .should('have.value', name)
    cy.getBySelectorId('smallName')
        .clear()
        .type(smallName)
        .should('have.value', smallName)
    cy.get('button[type=submit]').click()
}

describe('project creation and edition afterwards', () => {
    it('create project', () => {
        cy.visit('/projects')
        fillProjectFormAndSubmit(projectName, projectSmallName);
    })

    it('new project should appear in project hierarchies', () => {
        cy.goToHome();
        cy.contains(projectName);
        cy.contains(projectSmallName);
    })

    it('edit newly created project', () => {
        cy.navigateToProjectPage(projectName)
        cy.getBySelectorId('editProject').click();
        fillProjectFormAndSubmit(projectNewName, projectNewSmallName);
    })

    it('new name should appear in project hierarchies', () => {
        cy.goToHome();
        cy.contains(projectNewName);
        cy.contains(projectNewSmallName);
    })

    it('old name should not appear anywhere',() => {
        cy.goToHome();
        cy.contains(projectName).should('not.exist');
        cy.contains(projectSmallName).should('not.exist');
    })
})