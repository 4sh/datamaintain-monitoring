Cypress.Commands.add('getBySelectorId', (selectorId) => {
    cy.get('[data-cy="' + selectorId + '"]');
});

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

describe('project creation and edition afterwards', () => {
    it('create project', () => {
        cy.visit('/projects')
        cy.getBySelectorId('name')
            .type(projectName)
            .should('have.value', projectName)
        cy.getBySelectorId('smallName')
            .type(projectSmallName)
            .should('have.value', projectSmallName)
        cy.get('button[type=submit]').click()
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

        cy.getBySelectorId('name')
            .clear()
            .type(projectNewName)
            .should('have.value', projectNewName)
        cy.getBySelectorId('smallName')
            .clear()
            .type(projectNewSmallName)
            .should('have.value', projectNewSmallName)
        cy.get('button[type=submit]').click()
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