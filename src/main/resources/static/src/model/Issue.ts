export class Issue {
    private projectId: number;
    private issueType: number;
    private title: string;
    private startDate: string;
    private endDate: string;
    private version: string;
    private ownerId: number;
    private ownerName: string;
    private reporterId: number;
    private reporterName: String;
    private env: string;
    private label: string;
    private issueLinkType: string;
	private relationIssues: [];
}