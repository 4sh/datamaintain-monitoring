import {describe, expect, it} from "vitest";
import {HierarchySearch, SearchItem} from "../../../src/lib/domain/ProjectHierarchy";
import {ProjectMock} from "../../../src/lib/mocks/ProjectMock";

describe('SearchItem', () => {

	it('isClosed', () => {
		expect(new SearchItem(undefined).isClosed()).toBeTruthy()
		expect(new SearchItem(null).isClosed()).toBeFalsy()
		expect(new SearchItem('').isClosed()).toBeFalsy()
		expect(new SearchItem('Dat').isClosed()).toBeFalsy()
	})

	it('isOpened', () => {
		expect(new SearchItem(null).isOpened()).toBeTruthy()
		expect(new SearchItem('').isOpened()).toBeTruthy()
		expect(new SearchItem(undefined).isOpened()).toBeFalsy()
		expect(new SearchItem('Dat').isOpened()).toBeFalsy()
	})

	it('Closed SearchItem never matches', () => {
		const searchItem = new SearchItem(undefined);

		expect(searchItem.match('')).toBeFalsy()
		expect(searchItem.match('bla')).toBeFalsy()

	})

	it('Opened SearchItem always matches', () => {
		const searchItem = new SearchItem(null);

		expect(searchItem.match('')).toBeTruthy()
		expect(searchItem.match('bla')).toBeTruthy()
	})

	it('match', () => {
		const searchItem = new SearchItem('Dat');

		expect(searchItem.match('Dat')).toBeTruthy()
		expect(searchItem.match('Datamaintain')).toBeTruthy()
		// expect(searchItem.match('datamaintain')).toBeTruthy()
		// expect(searchItem.match('dàtamaintain')).toBeTruthy()
	})
})

describe('HierarchySearch', () => {

	// *********
	// Constructor
	it('constructor with one-level string', () => {
		const searchText = 'Dat';
		const search = new HierarchySearch(searchText);

		expect(search.project).toEqual(new SearchItem(searchText, false))
		expect(search.env).toEqual(new SearchItem(searchText, false))
		expect(search.module).toEqual(new SearchItem(searchText, false))
	})

	it('constructor with two-level string', () => {
		const searchText = 'Dat/QA';
		const search = new HierarchySearch(searchText);

		expect(search.project).toEqual(new SearchItem('Dat'))
		expect(search.env).toEqual(new SearchItem('QA', false))
		expect(search.module).toEqual(new SearchItem('QA', false))
	})

	it('constructor with three-level string', () => {
		const searchText = 'Dat/QA/module';
		const search = new HierarchySearch(searchText);

		expect(search.project).toEqual(new SearchItem('Dat'))
		expect(search.env).toEqual(new SearchItem('QA'))
		expect(search.module).toEqual(new SearchItem('module'))
	})

	it('constructor with three-level string with spaces', () => {
		const searchText = 'Dat / QA / module';
		const search = new HierarchySearch(searchText);

		expect(search.project).toEqual(new SearchItem('Dat'))
		expect(search.env).toEqual(new SearchItem('QA'))
		expect(search.module).toEqual(new SearchItem('module'))
	})

	// *********
	// isEmpty
	it('must be empty', () => {
		const search = new HierarchySearch('');

		expect(search.isEmpty()).toBeTruthy()
	})

	it('must not be empty', () => {
		const search = new HierarchySearch('Dat');

		expect(search.isEmpty()).toBeFalsy()
	})

	// *********
	// Filter
	it('no filter if empty', () => {
		const search = new HierarchySearch('');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(ProjectMock.getProjectHierarchies())
	})

	it('filter one-level', () => {
		const search = new HierarchySearch('Dat');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(
			ProjectMock.getProjectHierarchies().filter(project => project.id === 'dm')
		)
	})

	it('filter two-level', () => {
		const search = new HierarchySearch('Dat/QA');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(
			ProjectMock.getProjectHierarchies()
				.filter(project => project.id === 'dm')
				.map(project => {
					project.envs = project.envs.filter(env => env.id === 'dm-qa')
					return project;
				})
		)
	})

	it('filter two-level with first opened', () => {
		const search = new HierarchySearch('/QA');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(
			ProjectMock.getProjectHierarchies()
				.filter(project => project.id === 'dm'
					|| project.id === 'gh')
				.map(project => {
					project.envs = project.envs.
					filter(env => env.id === 'dm-qa'
						|| env.id === 'gh-qa')
					return project;
				})
		)
	})

	it('filter three-level', () => {
		const search = new HierarchySearch('Goog/Qual/You');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());


		const expectedHierarchy = ProjectMock.getProjectHierarchies()
			.filter(project => project.id === 'gg')
			.map(project => {
				project.envs = project.envs
					.filter(env => env.id === 'gg-pp')
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'gg-yt')
						return env
					});

				return project;
			});

		expect(projectHierarchies).toEqual(expectedHierarchy)
	})

	it('filter three-level with first opened', () => {
		const search = new HierarchySearch('/QA/Dat');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());


		const expectedHierarchy = ProjectMock.getProjectHierarchies()
			.filter(project => project.id === 'dm')
			.map(project => {
				project.envs = project.envs
					.filter(env => env.id === 'dm-qa')
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'dm-dm')
						return env
					});

				return project;
			});

		expect(projectHierarchies).toEqual(expectedHierarchy)
	})

	it('filter three-level with two first opened', () => {
		const search = new HierarchySearch('//Dat');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());


		const expectedHierarchy = ProjectMock.getProjectHierarchies()
			.filter(project => project.id === 'dm')
			.map(project => {
				project.envs = project.envs
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'dm-dm')
						return env
					});

				return project;
			});

		expect(projectHierarchies).toEqual(expectedHierarchy)
	})

	it('filter three-level with second opened', () => {
		const search = new HierarchySearch('G//G');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());


		const expectedHierarchy = ProjectMock.getProjectHierarchies()
			.filter(project => project.id === 'gh' || project.id === 'gg')
			.map(project => {
				project.envs = project.envs
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'gh-gp'
							|| module.id === 'gg-gp'
							|| module.id === 'gg-gs')
						return env
					});

				return project;
			});

		expect(projectHierarchies).toEqual(expectedHierarchy)
	})
})