import { RequestHelper } from './request/RequestHelper';
export class Render {
    private request: RequestHelper = new RequestHelper();

    public rendIssueTable(): void {
        this.request.get('/issue', null);
    }
}