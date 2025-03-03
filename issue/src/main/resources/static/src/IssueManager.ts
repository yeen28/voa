import { RequestHelper } from './request/RequestHelper';
import { NewIssue } from './model/NewIssue';
import { Render } from './Render';

export class IssueManager {
    private elCreateIssue: Element;
    private elContents: Element;
    private elGnb: Element;

    private request: RequestHelper = new RequestHelper();
    private render: Render = new Render();

    constructor() {
        this.elCreateIssue = document.getElementById('create-issue');
        this.elContents = document.getElementsByClassName('contents')[0];
        this.elGnb = document.getElementById('gnb');
    }

    public open(): void {
       this.elCreateIssue.classList.remove('hide');
       this.elGnb.classList.add('hidden');
       this.elContents.classList.add('hidden');
    }

    public cancel(): void {
        this.elCreateIssue.classList.add('hide');
        this.elGnb.classList.remove('hidden');
        this.elContents.classList.remove('hidden');
    }

    private getIssueStatus(id: string): string {
        const elIssueStatusSelect : HTMLSelectElement = document.getElementById(id) as HTMLSelectElement;
        return elIssueStatusSelect.options[elIssueStatusSelect.selectedIndex].value
    }

    private getIssueTypeId(id: string): string {
        const elIssueTypeSelect : HTMLSelectElement = document.getElementById(id) as HTMLSelectElement;
        return elIssueTypeSelect.options[elIssueTypeSelect.selectedIndex].value
    }

    private getIssueRank(id: string): string {
        const elIssueRankSelect : HTMLSelectElement = document.getElementById(id) as HTMLSelectElement;
        return elIssueRankSelect.options[elIssueRankSelect.selectedIndex].value
    }

    private getIssueOwner(id: string): string {
        const elIssueOwnerSelect : HTMLSelectElement = document.getElementById(id) as HTMLSelectElement;
        return elIssueOwnerSelect.options[elIssueOwnerSelect.selectedIndex].value
    }

    private getIssueReporter(id: string): string {
        const elIssueReporterSelect : HTMLSelectElement = document.getElementById(id) as HTMLSelectElement;
        return elIssueReporterSelect.options[elIssueReporterSelect.selectedIndex].value
    }

    private getIssueTitle(id: string): string {
        const elTitleInput: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elTitleInput.value;
    }

    private getIssueVersion(id: string): string {
        const elVersionInput: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elVersionInput.value;
    }

    private getIssueLabel(id: string): string {
        const elLabelInput: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elLabelInput.value;
    }

    private getIssueRelation(id: string): string {
        const elRelationInput: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elRelationInput.value;
    }

    private getIssueEnv(id: string): string {
        const elEnvContent: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elEnvContent.value;
    }

    private getIssueDesc(id: string): string {
        const elDescContent: HTMLInputElement = document.getElementById(id) as HTMLInputElement;
        return elDescContent.value;
    }

    public create(): void {
        const newIssueModel: NewIssue = new NewIssue(
            '1', //TODO projectId
            '',
            this.getIssueTypeId('issue-type-select'),
            this.getIssueTitle('issue-title-input'),
            this.getIssueLabel('issue-relation-input').split(','),
            this.getIssueVersion('issue-version-input').split(','),
            this.getIssueEnv('issue-env-content'),
            this.getIssueDesc('issue-desc-content'),
            '1', //TODO rank
            '1', //TODO ownerId
            '1', //TODO reporterId
            this.getIssueRelation('issue-relation-input')
        );

        const successFunc: Function = (newIssue: NewIssue) => {
            this.cancel();

            if (document.getElementById('issue-track-body').classList.contains('hide')) {
                this.render.rendIssueIntoTable(newIssue);
            } else {
                this.render.rendIssueIntoBoard(newIssue);
            }
        }

        const errorFunc: Function = (res: Response) => {
            console.log(res);
            this.cancel();
        }

        this.request.post('/issue', newIssueModel, successFunc, errorFunc);
    }

    public showVersions(): void {
        const type: string = (event.target as HTMLInputElement).getAttribute('data-type');
        const successFunc: Function = (versions: any) => {
            this.render.rendVersions(versions, type);
        }

        const errorFunc: Function = (res: Response) => {
            console.log(res);
        }
        this.request.get('versions', {}, successFunc, errorFunc);
    }

    public showLabels(): void {
        const type: string = (event.target as HTMLInputElement).getAttribute('data-type');
        const successFunc: Function = (labels: any) => {
            this.render.rendLabels(labels, type);
        }

        const errorFunc: Function = (res: Response) => {
            console.log(res);
        }
        this.request.get('labels', {}, successFunc, errorFunc);
    }

    public updateIssue(): void {
        //WIP request
        const issueModel: NewIssue = new NewIssue(
            '1', //TODO projectId
            this.getIssueStatus('issue-edit-status-select'),
            this.getIssueTypeId('issue-edit-type-select'),
            this.getIssueTitle('issue-edit-title-input'),
            this.getIssueLabel('issue-edit-label-input').split(','),
            this.getIssueVersion('issue-edit-version-input').split(','),
            this.getIssueEnv('issue-edit-env-content'),
            this.getIssueDesc('issue-edit-desc-content'),
            '1', //TODO rank
            '1', //TODO ownerId
            '1', //TODO reporterId
            this.getIssueRelation('issue-edit-relation-input')
        );
        
        const successFunc: Function = (newIssue: NewIssue) => {
            if (document.getElementById('issue-track-body').classList.contains('hide')) {
                this.render.rendIssueIntoTable(newIssue);
            } else {
                this.render.rendIssueIntoBoard(newIssue);
            }

            const elEditIssue: any = document.getElementById('edit-issue');
            elEditIssue.classList.add('hide');
        }

        const errorFunc: Function = (res: Response) => {
            const elEditIssue: any = document.getElementById('edit-issue');
            elEditIssue.classList.add('hide');

            console.log(res);
            this.cancel();
        }

        const issueId: string = document.getElementById('edit-issue').getAttribute('data-issue-id');
        this.request.put(`/issue/${issueId}`, issueModel, successFunc, errorFunc);
    }

    public deleteIssue(): void {
        const successFunc: Function = (newIssue: NewIssue) => {
            const elEditIssue: any = document.getElementById('edit-issue');
            elEditIssue.classList.add('hide');

            if (document.getElementById('issue-track-body').classList.contains('hide')) {
                this.render.rendIssueIntoTable(newIssue);
            } else {
                this.render.rendIssueIntoBoard(newIssue);
            }
        }

        const errorFunc: Function = (res: Response) => {
            console.log(res);
            const elEditIssue: any = document.getElementById('edit-issue');
            elEditIssue.classList.add('hide');
        }

        const issueId: string = document.getElementById('edit-issue').getAttribute('data-issue-id');
        this.request.delete(`/issue/${issueId}`, {}, successFunc, errorFunc);
    }

    public getUsers(): void {
        console.log('users');
        // WIP request
    }
}