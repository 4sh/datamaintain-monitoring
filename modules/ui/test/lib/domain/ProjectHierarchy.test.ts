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
		expect(searchItem.match('datamaintain')).toBeTruthy()
		expect(searchItem.match('dàtamaintain')).toBeTruthy()
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
			ProjectMock.getProjectHierarchies().filter(project => project.id === 'ecee7679-7197-432d-85a0-2d2d4da6096e')
		)
	})

	it('filter two-level', () => {
		const search = new HierarchySearch('Dat/QA');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(
			ProjectMock.getProjectHierarchies()
				.filter(project => project.id === 'ecee7679-7197-432d-85a0-2d2d4da6096e')
				.map(project => {
					project.envs = project.envs.filter(env => env.id === '109a2c08-e836-451a-86ef-d67be8ffc648')
					return project;
				})
		)
	})

	it('filter two-level with first opened', () => {
		const search = new HierarchySearch('/QA');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());

		expect(projectHierarchies).toEqual(
			ProjectMock.getProjectHierarchies()
				.filter(project => project.id === 'ecee7679-7197-432d-85a0-2d2d4da6096e'
					|| project.id === '6b383cf5-0712-478e-b590-9f82965476b5')
				.map(project => {
					project.envs = project.envs.
					filter(env => env.id === '109a2c08-e836-451a-86ef-d67be8ffc648'
						|| env.id === '1a72f8ae-e4cf-4812-8876-8c3eb4a3ee65')
					return project;
				})
		)
	})

	it('filter three-level', () => {
		const search = new HierarchySearch('Goog/Qual/You');

		const projectHierarchies = search.filter(ProjectMock.getProjectHierarchies());


		const expectedHierarchy = ProjectMock.getProjectHierarchies()
			.filter(project => project.id === '5e586e50-80e5-4a22-a91e-e266c3e01138')
			.map(project => {
				project.envs = project.envs
					.filter(env => env.id === 'd900abc9-032b-43f6-8666-0454d175ab10')
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === '52db1224-e09c-4d1b-a049-bab122faacd4')
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
			.filter(project => project.id === 'ecee7679-7197-432d-85a0-2d2d4da6096e')
			.map(project => {
				project.envs = project.envs
					.filter(env => env.id === '109a2c08-e836-451a-86ef-d67be8ffc648')
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'a78e96a7-6748-4f01-9691-ea3bf851ad43')
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
			.filter(project => project.id === 'ecee7679-7197-432d-85a0-2d2d4da6096e')
			.map(project => {
				project.envs = project.envs
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === 'a78e96a7-6748-4f01-9691-ea3bf851ad43')
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
			.filter(project => project.id === '6b383cf5-0712-478e-b590-9f82965476b5' || project.id === '5e586e50-80e5-4a22-a91e-e266c3e01138')
			.map(project => {
				project.envs = project.envs
					.map(env => {
						env.modules = env.modules
							.filter(module => module.id === '70dc790e-40d8-4d80-9500-75173629e47f'
							|| module.id === 'f50781d4-2f29-4338-9f39-a79d39f90614'
							|| module.id === '80ba4570-86c6-4e92-aaaf-9010207e1d56')
						return env
					});

				return project;
			});

		expect(projectHierarchies).toEqual(expectedHierarchy)
	})
})