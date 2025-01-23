<script>
	import { Label, Input, Button } from 'flowbite-svelte';

	let password = '';

	const updatePassword = () => {
		console.log(password);
	}
</script>

<div class="profile-wrap">
	<div class="profile-title">Profile</div>

	<div class="input-wrap">
		<div class="mb-6">
			<Label for="default-input" class="block mb-2">Change Nickname</Label>
			<Input id="default-input" placeholder="VOA" value="Jake" bind={password}/>
		</div>

		<div class="mb-6">
			<Label for="default-input" class="block mb-2">Change Password</Label>
			<Input id="default-input" placeholder="number, character, 8" />
		</div>
	</div>

	<Button color="blue" on:click={updatePassword}>SAVE</Button>
</div>

<style>
	.profile-wrap {
		width: 50%;
		margin: 80px 135px;
		display: grid;
		gap: 35px;
	}

	.profile-title {
		font-weight: bold;
		font-size: 1.8rem;
	}
</style>