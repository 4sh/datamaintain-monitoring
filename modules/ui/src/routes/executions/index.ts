/** @type {import('./__types/[id]/detail').RequestHandler} */
import {ExecutionService} from "../../lib/services/ExecutionService";


export async function get({params}: {params: {id: string}}) {
    const executions = await ExecutionService.search({});

    if (executions) {
        return {
            status: 200,
            headers: {},
            body: {
                executions
            }
        }
    }

    return {
        status: 404
    }
}
