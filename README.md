# COMP-3350 Project

A class group project aimed at developing a proper Android application.

## Contributing
***Please do not push your changes directly to the `main` branch.***

***All changes should be made on their respective branch and merged via a pull-request.***

### Cloning
To get the project on your machine, you must first clone it by running the following:
```
git clone https://github.com/Letharrick/comp-3350-project.git
```

### Creating a branch
While all of the code will reside in the `main` branch, when working on a particular feature for the project, you must create and work on a branch for that feature.

To create and switch to a new local branch, run the following:
```
git checkout <BRANCH_NAME> -b
```

This will create a new local branch called `BRANCH_NAME` and switch over to it from the `main` branch

### Making changes
Now that you are on a new branch for your feature, you can now start making some changes to the project.

Make as many changes as you'd like by editing, adding, or removing files.

### Staging changes
Once you are finished making your changes, you must then stage the changes to prepare for making a `commit` to your branch.

To stage the changes for a specific file, run the following:
```
git add <FILE_PATH>
```
Alternatively, you can stage all of your changes at once by heading to the root of the project directory and running:
```
git add .
```

### Committing changes
Once your changes have been staged, you can then create a `commit` with a message describing your changes by running:

```
git commit -m "<MESSAGE>"
```

The `commit` message is not very important, but try to add at least a sentence describing the gist of the changes that are being committed.

Also, feel free to create multiple `commit`s on your branch to break up the work if it is you are working on a feature that consists of a lot of changes.

### Pushing changes
Once your changes have been committed, you can then push your `commit`s up to the repository.

If it is your first time `push`ing on your local branch, then you must run the following:
```
git push -u origin <BRANCH_NAME>
```

If it is not your first `push` on that branch, then the following will suffice:
```
git push
```

Don't worry if you forget this detail, as `git` will actually remind you about this and give you a message with the correct command to use in the case that you chose the wrong one.

### Creating a pull-request
For the final step, you will need to create a pull-request which describes the feature that was implemented on the branch and *requests* for your branch to be *pulled* into the `main` branch.

To do this, visit the [GitHub repository](https://github.com/Letharrick/comp-3350-project) and you will see a yellow-ish banner stating that you have changes on a branch, and on that banner will be a green button that says `Compare & Pull Request`, click this to create your pull-request.

For your pull-request, please provide an informative title and description which provides details regarding the changes you've made.

Once finished, open the pull-request and notify the group in Discord.

Once your changes have been discussed and approved by the group, your pull-request will then be merged into `main`!