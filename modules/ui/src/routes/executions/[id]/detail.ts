import {ExecutionService} from "../../../lib/services/ExecutionService";

/** @type {import('./__types/[id]/detail').RequestHandler} */
export async function get({params}: {params: {id: string}}) {
    const execution = await ExecutionService.byId(params.id);


    if (execution) {
        return {
            status: 200,
            headers: {},
            body: {
                execution
            }
        }
    }

    return {
        status: 404
    }
}
