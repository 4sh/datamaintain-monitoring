function generateId(): string {
    return Math.random().toString(16).slice(2);
}

function buildProjectName(id): string {
    return 'project ' + id;
}

function buildProjectSmallName(id): string {
    return id;
}

const projectId = generateId();
const projectName = buildProjectName(projectId);
const projectSmallName = buildProjectSmallName(projectId);

const newProjectId = generateId();
const projectNewName = buildProjectName(newProjectId)
const projectNewSmallName = buildProjectSmallName(newProjectId);

Cypress.Commands.add('fillProjectForm', (name, smallName) => {
    cy.getBySelectorId('name')
        .clear()
        .type(name)
        .should('have.value', name)
    cy.getBySelectorId('smallName')
        .clear()
        .type(smallName)
        .should('have.value', smallName)
    cy.get('button[type=submit]').click()
})

describe('project creation and edition afterwards', () => {
    it('create project', () => {
        cy.visit('/projects')
        cy.fillProjectForm(projectName, projectSmallName);
    })

    it('new project should appear in project hierarchies', () => {
        cy.visit('/')
        cy.contains(projectName);
        cy.contains(projectSmallName);
    })

    it('edit newly created project', () => {
        cy.visit('/')
        cy.contains(projectName).click();
        cy.getBySelectorId('editProject').click();
        cy.fillProjectForm(projectNewName, projectNewSmallName);
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