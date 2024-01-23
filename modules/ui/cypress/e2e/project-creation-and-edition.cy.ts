import {generateId} from '../support/utils';

function buildProjectName(id: string): string {
    return 'project ' + id;
}

function buildProjectSmallName(id: string): string {
    return id;
}

const projectId = generateId();
const projectName = buildProjectName(projectId);
const projectSmallName = buildProjectSmallName(projectId);

const newProjectId = generateId();
const projectNewName = buildProjectName(newProjectId)
const projectNewSmallName = buildProjectSmallName(newProjectId);

/**
 * Fills project form with given arguments and then clicks the submit button.
 * Can be used either to create a new project or edit an already existing one

 * Pre-requisite: Be on the project creation/edition page
 *
 * @param name Name to give to your project
 * @param smallName Small name to give to your project
 */
function fillProjectForm(name: string, smallName: string) {
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
        fillProjectForm(projectName, projectSmallName);
    })

    it('new project should appear in project hierarchies', () => {
        cy.visit('/')
        cy.contains(projectName);
        cy.contains(projectSmallName);
    })

    it('edit newly created project', () => {
        cy.navigateToProjectPage(projectName)
        cy.getBySelectorId('editProject').click();
        fillProjectForm(projectNewName, projectNewSmallName);
    })

    it('new name should appear in project hierarchies', () => {
        cy.visit('/')
        cy.contains(projectNewName);
        cy.contains(projectNewSmallName);
    })

    it('old name should not appear anywhere',() => {
        cy.visit('/')
        cy.contains(projectName).should('not.exist');
        cy.contains(projectSmallName).should('not.exist');
    })
})