import { NewIssue } from './model/NewIssue';
import { RequestHelper } from './request/RequestHelper';
export class Render {
    private request: RequestHelper = new RequestHelper();

    /**
     * 보드의 이슈들을 모두 제거합니다.
     */
    public clearBoard(): void {
        Array.from(document.getElementsByClassName('issue-card')).forEach((elIssue) => {
            elIssue.remove();
        });
    }

    /**
     * 테이블의 이슈들을 모두 제거합니다.
     */
    public clearTable(): void {
        Array.from(document.getElementsByClassName('row')).forEach((elIssue) => {
            elIssue.remove();
        });
    }

    /**
     * 보드 아이콘 클릭 이벤트
     */
    public viewBoard(): void {
        this.clearBoard();

        document.getElementById('issue-track-body').classList.remove('hide');
        document.getElementById('issue-table').classList.add('hide');
        this.rendBoard();
    }

    /**
     * 테이블 아이콘 클릭 이벤트
     */
    public viewTable(): void {
        this.clearTable();

        document.getElementById('issue-table').classList.remove('hide');
        document.getElementById('issue-track-body').classList.add('hide');
        this.rendTable();
    }

    /**
     * 테이블의 이슈들을 그립니다.
     */
    public rendTable(): void {
        const successFunc: Function = (issues: NewIssue[]) => {
            issues.forEach((issue: NewIssue) => {
                this.rendIssueIntoTable(issue);
            });
        };

        const errorFunc: Function = (res: Response) => {
            console.log(res);
        }
        this.request.get('/issues', {ownerId:1}, successFunc, errorFunc);
    }

    /**
     * 이슈를 테이블에 그립니다.
     * @param issue 
     */
    public rendIssueIntoTable(issue: NewIssue): void {
        let elRowIssue: any = document.createElement('tr');
        elRowIssue.classList.add('row')
        elRowIssue.innerHTML = (document.getElementById('template-table-item') as HTMLTextAreaElement).value;

        elRowIssue.getElementsByClassName('table-issue-title')[0].textContent = issue.title;
        elRowIssue.getElementsByClassName('table-issue-type')[0].textContent = issue.issueType;
        elRowIssue.getElementsByClassName('table-issue-owner')[0].textContent = issue.ownerName;
        elRowIssue.getElementsByClassName('table-issue-reporter')[0].textContent = issue.ownerName;
        elRowIssue.getElementsByClassName('table-issue-version')[0].textContent = issue.versionNames;
        elRowIssue.getElementsByClassName('table-issue-createdAt')[0].textContent = issue.createdAt;

        document.getElementById('issue-table-tbody').appendChild(elRowIssue);
    }

    /**
     * 보드에 이슈들을 그립니다.
     */
    public rendBoard(): void {
        const successFunc: Function = (issues: NewIssue[]) => {
            issues.forEach((issue: NewIssue) => {
                this.rendIssueIntoBoard(issue);
            });
        };

        const errorFunc: Function = (res: Response) => {
             console.log(res);
        }
        this.request.get('/issues', {ownerId: 1}, successFunc, errorFunc);
    }

    /**
     * 이슈를 보드에 그립니다.
     * @param issue 
     */
    public rendIssueIntoBoard(issue: NewIssue): void {
        let elIssueCard: any = document.createElement('div');
        elIssueCard.classList.add('issue-card')
        elIssueCard.innerHTML = (document.getElementById('template-new-issue') as HTMLTextAreaElement).value;

        elIssueCard.getElementsByClassName('issue-card-title-text')[0].textContent = issue.title;
        elIssueCard.getElementsByClassName('issue-card-type')[0].textContent = issue.issueType;
        elIssueCard.getElementsByClassName('issue-card-user')[0].textContent = issue.ownerName;
        elIssueCard.getElementsByClassName('issue-card-version')[0].textContent = issue.versionNames;
        elIssueCard.getElementsByClassName('issue-card-label')[0].textContent = issue.labelNames;

        document.getElementsByClassName('issue-todo-item-wrap')[0].appendChild(elIssueCard);
    }

    public rendVersions(): void {
        document.getElementById('issue-label-select').classList.add('hide');
        document.getElementById('issue-version-select').classList.remove('hide');
    }

    public rendLabels(): void {
        document.getElementById('issue-version-select').classList.add('hide');
        document.getElementById('issue-label-select').classList.remove('hide');
    }
}