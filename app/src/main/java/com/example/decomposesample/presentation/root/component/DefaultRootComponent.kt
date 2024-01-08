package com.example.decomposesample.presentation.root.component

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.presentation.addcontact.component.DefaultAddContactComponent
import com.example.decomposesample.presentation.contactlist.component.DefaultContactListComponent
import com.example.decomposesample.presentation.editcontact.component.DefaultEditContactComponent
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<NavConfig>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = NavConfig.ContactList,
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(
        navConfig: NavConfig,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (navConfig) {
            NavConfig.AddContact -> {
                val component = DefaultAddContactComponent(
                    componentContext = componentContext,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.AddContact(component)
            }

            NavConfig.ContactList -> {
                val component = DefaultContactListComponent(
                    componentContext = componentContext,
                    onAddContactRequested = {
                        navigation.push(NavConfig.AddContact)
                    },
                    onEditingContactRequested = {
                        navigation.push(NavConfig.EditContact(contact = it))
                    }
                )
                RootComponent.Child.ContactList(component)
            }

            is NavConfig.EditContact -> {
                val component = DefaultEditContactComponent(
                    componentContext = componentContext,
                    contact = navConfig.contact,
                    onContactSaved = {
                        navigation.pop()
                    }
                )
                RootComponent.Child.EditContact(component)
            }
        }
    }

    private sealed interface NavConfig : Parcelable {

        @Parcelize
        object ContactList : NavConfig

        @Parcelize
        object AddContact : NavConfig

        @Parcelize
        data class EditContact(val contact: Contact) : NavConfig
    }
}